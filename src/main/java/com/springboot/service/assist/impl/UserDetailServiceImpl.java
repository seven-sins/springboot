package com.springboot.service.assist.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.config.exception.SevenException;
import com.springboot.mapper.sys.UserMapper;
import com.springboot.po.Privilege;
import com.springboot.po.User;
import com.springboot.service.assist.UserDetailService;
import com.springboot.service.sys.RolePrivilegeService;
import com.springboot.vo.OauthUser;

@Service
public class UserDetailServiceImpl implements UserDetailService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	RolePrivilegeService rolePrivilegeService;
	
	private String passWord;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findByName(username);
		if (user == null) {
			throw new SevenException(401, "用户未找到: '" + username + "'");
		}
		if (user.getPassWord() == null || !user.getPassWord().equals(this.passWord)) {
			throw new SevenException(401, "用户密码不正确");
		}
//		boolean isLock = (user.getStatus() != null && user.getStatus().equals(0)) ? true : false;
//		if(isLock){
//			throw new SevenException(401, "用户已禁用");
//		}
		Collection<SimpleGrantedAuthority> collection = new HashSet<SimpleGrantedAuthority>();
		collection.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		List<Privilege> privilegeList = rolePrivilegeService.findByRoleId(user.getRoleId());

		OauthUser oauthUser = new OauthUser(username, user.getPassWord(), collection);
		oauthUser.setRoleId(user.getRoleId());
		oauthUser.setPrivileges(privilegeList);

		return oauthUser;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPassWord() {
		return passWord;
	}

}
