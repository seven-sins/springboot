package com.springboot.domain;

import com.springboot.base.Base;
import com.springboot.config.annotation.Rule;

import java.io.Serializable;

public class Role extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@Rule(max = 20, desc = "名称")
	private String name;
	@Rule(max = 1, desc = "状态")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
