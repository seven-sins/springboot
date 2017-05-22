package com.springboot.service.sys;

import java.util.Map;

import com.springboot.base.BaseService;
import com.springboot.po.MenuCategory;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:01:45
 */
public interface MenuCategoryService extends BaseService<MenuCategory>{

	public void updateModules(Map<String, Object> map);
}
