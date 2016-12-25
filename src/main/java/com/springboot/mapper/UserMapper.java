package com.springboot.mapper;

import com.springboot.base.BaseMapper;
import com.springboot.domain.User;

public interface UserMapper extends BaseMapper<User>{ //
	/**
	 * 用户登录
	 * @param entity
	 * @return
	 */
	public User doLogin(User entity);
}
