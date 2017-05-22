package com.springboot.mapper.sys;

import com.springboot.base.BaseMapper;
import com.springboot.po.User;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:01:36
 */
public interface UserMapper extends BaseMapper<User>{ //
	/**
	 * 用户登录
	 * @param entity
	 * @return
	 */
	User doLogin(User entity);
	
	User findByName(String userName);
}
