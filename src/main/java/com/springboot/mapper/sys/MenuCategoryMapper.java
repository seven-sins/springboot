package com.springboot.mapper.sys;

import java.io.Serializable;
import java.util.Map;

import com.springboot.base.BaseMapper;
import com.springboot.po.MenuCategory;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:01:08
 */
public interface MenuCategoryMapper extends BaseMapper<MenuCategory> {

	/**
	 * 将menu_category_id = id的项重置为0
	 * @param id
	 */
	public void removeModules(Serializable id);
	/**
	 * 设置modules
	 * @param map
	 */
	public void updateModules(Map<String, Object> map);
}
