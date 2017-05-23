package com.springboot.config.security;

import com.alibaba.fastjson.JSONObject;
import com.springboot.config.security.filter.AuthenticationTokenFilter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Tan Ling
 * @date 2017年5月5日 下午4:39:37
 */
@Configuration
@EnableWebSecurity
@EnableAuthorizationServer
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Bean
	public AuthenticationTokenFilter authenticationTokenFilter() {
		return new AuthenticationTokenFilter();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//
	        .and().csrf().disable()//
	        .authorizeRequests().antMatchers("/api/**").authenticated();

	    http.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())//
	        .and().addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);//
	}

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

	// 方法无效，等后续测试
	// @Bean
	// public AccessDecisionManager shellAccessDecisionManager() {
	// List<AccessDecisionVoter<?>> voters = new
	// ArrayList<AccessDecisionVoter<?>>();
	// RoleVoter voter = new RoleVoter();
	// voter.setRolePrefix("");
	// voters.add(voter);
	// return new UnanimousBased(voters);
	// }

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
