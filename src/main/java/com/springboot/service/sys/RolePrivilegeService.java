package com.springboot.service.sys;

import java.util.List;
import java.util.Map;

import com.springboot.base.BaseService;
import com.springboot.po.Privilege;
import com.springboot.po.RolePrivilege;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:02:10
 */
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
