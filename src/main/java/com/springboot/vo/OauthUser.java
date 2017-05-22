package com.springboot.vo;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author seven sins
 * @date 2017年5月22日 下午6:55:09
 */
public class OauthUser extends User{

	private static final long serialVersionUID = 1L;
	
	public OauthUser(){
		super("user", "pwd", new ArrayList<GrantedAuthority>());
	}

	public OauthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
}
