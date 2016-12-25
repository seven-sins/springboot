package com.springboot.mapper;

import com.springboot.base.BaseMapper;
import com.springboot.domain.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole>{ //
	
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
