package com.springboot.service.assist.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.config.exception.SevenException;
import com.springboot.mapper.sys.UserMapper;
import com.springboot.po.User;
import com.springboot.po.UserDetail;
import com.springboot.service.assist.UserDetailService;

@Service
public class UserDetailServiceImpl implements UserDetailService{

	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findByName(username);
        if (user == null) {
            throw new SevenException(400, "Could not find the user '" + username + "'");
        }
        boolean isLock = (user.getStatus() != null && user.getStatus().equals(0)) ? true : false;
        UserDetail userDetail = new UserDetail(user, true, true, true, isLock);
        userDetail.setRoleId(user.getRoleId());
        
        return userDetail;
	}
}
