package com.springboot.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.domain.User;
import com.springboot.mapper.UserMapper;
import com.springboot.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public List<User> find(User entity) {
		return userMapper.find(entity);
	}

	@Override
	public User get(Serializable id) {
		return userMapper.get(id);
	}

	@Override
	public void insert(User entity) {
		userMapper.insert(entity);	
		entity.setId(100);
		userMapper.insert(entity);
		entity.setId(101);
		userMapper.insert(entity);
	}

	@Override
	public void update(User entity) {
		userMapper.update(entity);		
	}

	@Override
	public void deleteById(Serializable id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Serializable[] ids) {
		// TODO Auto-generated method stub
		
	}
}
