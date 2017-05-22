package com.springboot.po;

import com.springboot.base.Base;
import com.springboot.config.annotation.Rule;

import java.io.Serializable;
import java.util.List;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:00:31
 */
public class Privilege extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@Rule(max = 30, desc = "名称")
	private String name;
	private String url;
	@Rule(max = 10, desc = "请求方式")
	private String method;
	@Rule(max = 1, desc = "类型")
	private Integer type; // 0:菜单分类，1:模块，2:接口
	private Integer parentId;
	@Rule(max = 1, desc = "状态")
	private Integer status;
	private Integer menuCategoryId; // 所属菜单分类， 仅type = 0 有效
	private String menuCategoryName; // 所属菜单分类名称
	private Integer position;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method == null ? "" : method;
	}

	public List<Privilege> getChildren() {
		return children;
	}

	public void setChildren(List<Privilege> children) {
		this.children = children;
	}

	public Integer getMenuCategoryId() {
		return menuCategoryId;
	}

	public void setMenuCategoryId(Integer menuCategoryId) {
		this.menuCategoryId = menuCategoryId;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getMenuCategoryName() {
		return menuCategoryName;
	}

	public void setMenuCategoryName(String menuCategoryName) {
		this.menuCategoryName = menuCategoryName;
	}
	
}
