package com.springboot.service;

import com.springboot.base.BaseService;
import com.springboot.domain.User;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:02:31
 */
public interface UserService extends BaseService<User> {
	/**
	 * 用户登录
	 * @param entity
	 * @return
	 */
	public User doLogin(User entity);
}
