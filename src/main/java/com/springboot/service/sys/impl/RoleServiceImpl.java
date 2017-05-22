package com.springboot.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.springboot.mapper.sys.RoleMapper;
import com.springboot.po.Role;
import com.springboot.service.sys.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleMapper roleMapper;
	
	@Override
	public List<Role> find(Role entity) {
		PageHelper.startPage(entity.getIndex(), entity.getSize());
		return roleMapper.find(entity);
	}

	@Override
    @Cacheable(value = "roleCache", keyGenerator = "wiselyKeyGenerator")
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
		roleMapper.deleteById(id);
	}

	@Override
	public void delete(Serializable[] ids) {
		
	}

	@Override
	public int count(Role entity) {
		return roleMapper.count(entity);
	}

}
