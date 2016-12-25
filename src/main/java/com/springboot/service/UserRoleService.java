package com.springboot.service;

import com.springboot.base.BaseService;
import com.springboot.domain.UserRole;

public interface UserRoleService extends BaseService<UserRole>{

	/**
	 * 根据用户Id删除
	 * @param id
	 */
	public void deleteByUserId(Integer id);
	/**
	 * 根据角色Id删除
	 * @param id
	 */
	public void deleteByRoleId(Integer id);
}
