package com.springboot.domain;

import com.springboot.base.Base;
import com.springboot.config.annotation.Rule;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:00:55
 */
public class User extends Base implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	@Rule(max = 20, desc = "用户名")
	private String userName;
	@Rule(desc = "密码")
	private String passWord;
	@Rule(desc = "邮箱")
	private String email;
	@Rule(desc = "昵称")
	private String nickName;
	private String avatar;
	@Rule(nullable = true, desc = "电话号码")
	private String phoneNumber;
	@Rule(desc = "状态", max = 1)
	private Integer status;
	@Rule(desc = "角色")
	private Integer roleId;
	private String roleName;
	private Date birthday;
	List<Privilege> privileges;
	
	public User(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}
}
