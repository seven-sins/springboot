package com.springboot.config.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.config.exception.SevenException;
import com.springboot.service.assist.RedisService;
import com.springboot.vo.OauthUser;

/**
 * @author Tan Ling
 * @date 2017年5月5日 下午4:39:15
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter{
  
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
          if(accessToken != null){
            if(accessToken.isExpired()){
              tokenStore.removeAccessToken(accessToken);
              throw new SevenException(1, "token已过期");
            }
            Object object = redisService.get(authToken);
            if(object == null){
              tokenStore.removeAccessToken(accessToken);
              throw new SevenException(1, "token已过期");
            }
            OauthUser oauthUser = (OauthUser) object;
            
              UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                  oauthUser, null, oauthUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                        request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
          }else{
            filterChain.doFilter(request, response);
          }
        }

        filterChain.doFilter(request, response);
  }

}
