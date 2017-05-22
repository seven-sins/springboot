package com.springboot.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.springboot.mapper.sys.UserMapper;
import com.springboot.po.User;
import com.springboot.service.sys.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public List<User> find(User entity) {
		PageHelper.startPage(entity.getIndex(), entity.getSize());
		return userMapper.find(entity);
	}

	@Override
	public User get(Serializable id) {
		return userMapper.get(id);
	}

	@Override
	public void insert(User entity) {
		userMapper.insert(entity);	
	}

	@Override
	public void update(User entity) {
		userMapper.update(entity);		
	}

	@Override
	public void deleteById(Serializable id) {
		userMapper.deleteById(id);
	}

	@Override
	public void delete(Serializable[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User doLogin(User entity) {
		return userMapper.doLogin(entity);
	}

	@Override
	public int count(User entity) {
		return userMapper.count(entity);
	}
}
