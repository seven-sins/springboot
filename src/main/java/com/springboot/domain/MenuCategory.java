package com.springboot.domain;

import java.io.Serializable;
import java.util.List;

import com.springboot.base.Base;
import com.springboot.config.annotation.Rule;

public class MenuCategory extends Base implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@Rule(desc="菜单分类名称", max=30)
	private String name;
	private Integer position;
	private String method = "get";
	private Integer type;
	@Rule
	private String url;
	@Rule(desc="状态", max=1)
	private Integer status;
	private Integer parentId;
	private List<Privilege> children;
	
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
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public List<Privilege> getChildren() {
		return children;
	}
	public void setChildren(List<Privilege> children) {
		this.children = children;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
}
