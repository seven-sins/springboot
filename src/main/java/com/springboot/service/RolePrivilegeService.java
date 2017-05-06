package com.springboot.service;

import java.util.List;
import java.util.Map;

import com.springboot.base.BaseService;
import com.springboot.domain.Privilege;
import com.springboot.domain.RolePrivilege;

public interface RolePrivilegeService extends BaseService<RolePrivilege> {

	/**
	 * 设置角色权限
	 * @param roleId
	 * @param map
	 */
	public void update(int roleId, Map<String, Object> map);
	/**
	 * 根据角色id获取权限id列表
	 * @param roleId
	 * @return
	 */
	public List<Integer> getPrivilegeByRoleId(int roleId);
	/**
	 * 根据角色id获取权限列表
	 * @param privilege
	 * @return
	 */
	public List<Privilege> findByRoleId(int roleId);
}