package com.springboot.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.config.exception.SevenException;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:01:01
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetail extends User implements UserDetails, Serializable{

	private static final long serialVersionUID = 1L;
	private final boolean enabled;
	private final boolean accountNonExpired;
	private final boolean credentialsNonExpired;
	private final boolean accountNonLocked;
	private final Set<GrantedAuthority> authorities;
	
	public UserDetail(){
		this.enabled = true;
		this.accountNonExpired = false;
		this.credentialsNonExpired = false;
		this.accountNonLocked = false;
		this.authorities = new HashSet<>();
	}

	public UserDetail(User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked) {
		if (user != null && !"".equals(user.getUserName()) && !"".equals(user.getPassWord())) {
			super.setUserName(user.getUserName());
			super.setPassWord(user.getPassWord());
			this.enabled = enabled;
			this.accountNonExpired = accountNonExpired;
			this.credentialsNonExpired = credentialsNonExpired;
			this.accountNonLocked = accountNonLocked;
			this.authorities = new HashSet<>();
		} else {
			throw new SevenException(401, "Cannot pass null or empty values to constructor");
		}
	}

	@Override
	public String getPassword() {
		return super.getPassWord();
	}

	@Override
	public String getUsername() {
		return super.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
}
