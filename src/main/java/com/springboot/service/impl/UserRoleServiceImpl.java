package com.springboot.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.domain.UserRole;
import com.springboot.mapper.UserRoleMapper;
import com.springboot.service.UserRoleService;

/**
 * 
 * @author seven sins
 *
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	UserRoleMapper userRoleMapper;

	@Override
	public List<UserRole> find(UserRole entity) {
		return userRoleMapper.find(entity);
	}

	@Override
	public UserRole get(Serializable id) {
		return null;
	}

	@Override
	public void insert(UserRole entity) {
		
	}

	@Override
	public void update(UserRole entity) {
		
	}

	@Override
	public void deleteById(Serializable id) {
		
	}

	@Override
	public void delete(Serializable[] ids) {
		
	}

	@Override
	public void deleteByUserId(Integer id) {
		userRoleMapper.deleteByUserId(id);		
	}

	@Override
	public void deleteByRoleId(Integer id) {
		userRoleMapper.deleteByRoleId(id);		
	}
	

}
