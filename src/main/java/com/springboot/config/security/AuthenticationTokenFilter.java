package com.springboot.config.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.config.exception.SevenException;
import com.springboot.po.Privilege;
import com.springboot.po.UserDetail;
import com.springboot.service.assist.RedisService;
import com.springboot.service.sys.PrivilegeService;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	PrivilegeService privilegeService;
	@Autowired
	RedisService redisService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authToken = request.getHeader("token");
        if (authToken != null) {
        	Object object = redisService.get("user-" + authToken);
			if(object == null){ // 缓存中没有用户信息
				throw new SevenException(401, "token已过期");
			}
			UserDetail user = null;
			try {
				user = (UserDetail) object;
			} catch (RuntimeException e) {
				throw new SevenException(401, "解析缓存用户信息出错");
			}
			/**
			 * 1. 获取当前访问url与method
			 */
			String url = request.getRequestURI();
			int index = url.indexOf("?");
			if(index != -1){
				url = url.substring(0, index);
			}
			String method = request.getMethod(); // eg: GET
			method = method.toLowerCase();
			/**
			 * 2. 检查访问权限
			 */
			if(!this.isValid(url, method, user.getPrivileges())){
				throw new SevenException(401, "无权限访问");
			}
			/**
			 * next
			 */
        	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        			user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                    request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
	}
	
	/**
	 * 权限检查
	 * @param url	当前url
	 * @param method	请求类型 
	 * @param privileges	权限列表
	 * @return
	 */
	private boolean isValid(String url, String method, List<Privilege> privileges){
		for(int i=0; i<privileges.size(); i++){
			Privilege privilege = privileges.get(i);
			String dbUrl = privilege.getUrl();
			dbUrl = dbUrl.replaceAll("\\{(\\w*|\\s*)\\}", "\\\\w*");
			
			if(url.matches(dbUrl) && method.equals(privilege.getMethod())){
				return true;
			}
		}
		return false;
	}
}
