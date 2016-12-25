package com.springboot.service;

import com.springboot.base.BaseService;
import com.springboot.domain.User;

public interface UserService extends BaseService<User>{
	/**
	 * 用户登录
	 * @param entity
	 * @return
	 */
	public User doLogin(User entity);
}
