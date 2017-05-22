package com.springboot.po;

import java.io.Serializable;

import com.springboot.base.Base;
import com.springboot.config.annotation.Rule;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:00:49
 */
public class TopicCategory extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@Rule(desc = "名称", max = 30)
	private String name;
	private Integer position;
	@Rule(desc = "状态", max = 1)
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

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
