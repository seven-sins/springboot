package com.springboot.po;

import java.util.List;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:00:43
 */
public class RolePrivilege {

	private Integer roleId;
	private Integer privilegeId;
	
	private List<Privilege> list;
	
	public RolePrivilege() {
		super();
	}
	
	public RolePrivilege(Integer roleId, Integer privilegeId) {
		super();
		this.roleId = roleId;
		this.privilegeId = privilegeId;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

	public List<Privilege> getList() {
		return list;
	}

	public void setList(List<Privilege> list) {
		this.list = list;
	}
	
}
