package com.springboot.config.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.config.exception.SevenException;
import com.springboot.po.Privilege;
import com.springboot.service.assist.RedisService;
import com.springboot.vo.OauthUser;

/**
 * @author Tan Ling
 * @date 2017年5月5日 下午4:39:15
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	TokenStore tokenStore;
	@Autowired
	RedisService redisService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getServletPath();
		String authToken = request.getHeader("Authorization");
		if (authToken != null && !"/oauth/token".equals(path)) {
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(authToken);
			if (accessToken != null) {
				if (accessToken.isExpired()) {
					tokenStore.removeAccessToken(accessToken);
					throw new SevenException(401, "token已过期");
				}
				Object object = redisService.get(authToken);
				if (object == null) {
					tokenStore.removeAccessToken(accessToken);
					throw new SevenException(401, "token已过期");
				}
				OauthUser oauthUser = (OauthUser) object;
				
				//
				String url = request.getRequestURI();
				int index = url.indexOf("?");
				if(index != -1){
					url = url.substring(0, index);
				}
				String method = request.getMethod(); // eg: GET
				method = method.toLowerCase();
				if(!this.isValid(path, method, oauthUser.getAuthorities())){
					throw new SevenException(403, "无权限访问");
				}

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(oauthUser,
						null, oauthUser.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				filterChain.doFilter(request, response);
			}
		}

		filterChain.doFilter(request, response);
	}
	
	private boolean isValid(String url, String method, Collection<GrantedAuthority> privileges){
		for(int i=0; i<privileges.size(); i++){
			String dbUrl = String.valueOf(privileges[i]);
			dbUrl = dbUrl.replaceAll("\\{(\\w*|\\s*)\\}", "\\\\w*");
			
			if(url.matches(dbUrl) && method.equals("")){ // privilege.getMethod()
				return true;
			}
		}
		return false;
	}

}
