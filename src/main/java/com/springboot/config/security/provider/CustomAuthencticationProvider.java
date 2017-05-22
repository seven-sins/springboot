package com.springboot.config.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.service.assist.UserDetailService;

/**
 * @author Tan Ling
 * @date 2017年5月5日 下午4:39:21
 */
public class CustomAuthencticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	UserDetailService userDetailService;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// System.out.println("principal: " + authentication.getPrincipal());
		// System.out.println("credentials: " +
		// authentication.getCredentials());
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		userDetailService.setPassWord(String.valueOf(authentication.getCredentials()));
		UserDetails oauthUser = userDetailService.loadUserByUsername(username);

		return oauthUser;
	}

}
