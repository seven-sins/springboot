package com.springboot.mapper;

import java.util.List;

import com.springboot.base.BaseMapper;
import com.springboot.domain.Privilege;
import com.springboot.domain.RolePrivilege;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:01:25
 */
public interface RolePrivilegeMapper extends BaseMapper<RolePrivilege>{ //

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
