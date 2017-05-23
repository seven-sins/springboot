package com.springboot.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.springboot.po.Privilege;

/**
 * seven sins
 * 2017年5月23日 上午9:14:25
 */
public class OauthUser extends User{

	private static final long serialVersionUID = 9201540820621868562L;

	private Integer roleId; 
	
	private List<Privilege> privileges = new ArrayList<Privilege>();
	
	public OauthUser(){
		super("user", "pwd", new ArrayList<GrantedAuthority>());
	}
	
	public OauthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}
	
}
