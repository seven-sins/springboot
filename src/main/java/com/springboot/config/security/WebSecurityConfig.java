package com.springboot.config.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alibaba.fastjson.JSONObject;
import com.springboot.service.RedisService;
import com.springboot.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;
	@Autowired
	RedisService redisService;
	@Autowired
	AuthenticationTokenFilter authenticationTokenFilter;

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 允许对于网站静态资源的无授权访问
		web.ignoring().antMatchers("/css/**", "/views/**", "/dist/**", "/plugin/**", "/favicon*");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// 基于token，所以不需要session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//
				.and().csrf().disable()//
				.authorizeRequests()//
				// 对于获取token的rest api要允许匿名访问
				.antMatchers("/login", "/doLogin", "/main", "/").permitAll()//
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated();
		http
				//
				.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())//
				.and().addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * 未授权
	 * 
	 * @return
	 */
	@Bean
	public AuthenticationEntryPoint unauthorizedEntryPoint() {
		return new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");
				PrintWriter out = null;
				try {
					JSONObject result = new JSONObject();
					result.put("code", "401");
					result.put("message", "无权限访问");
					out = response.getWriter();
					out.append(result.toString());
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}
		};
	}

	/**
	 * ******************************************************************************************************************
	 */
}
