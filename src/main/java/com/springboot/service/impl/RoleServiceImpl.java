package com.springboot.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.domain.Role;
import com.springboot.mapper.RoleMapper;
import com.springboot.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleMapper roleMapper;
	
	@Override
	public List<Role> find(Role entity) {
		return roleMapper.find(entity);
	}

	@Override
	public Role get(Serializable id) {
		return roleMapper.get(id);
	}

	@Override
	public void insert(Role entity) {
		roleMapper.insert(entity);	
	}

	@Override
	public void update(Role entity) {
		roleMapper.update(entity);		
	}

	@Override
	public void deleteById(Serializable id) {
		
	}

	@Override
	public void delete(Serializable[] ids) {
		
	}

}
