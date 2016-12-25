package com.springboot.domain;

public class Role {

	private Integer id;
	private String roleName;
	private Integer isDesiabled;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getIsDesiabled() {
		return isDesiabled;
	}
	public void setIsDesiabled(Integer isDesiabled) {
		this.isDesiabled = isDesiabled;
	}
	
}
