package com.springboot.service;

import java.util.Map;

import com.springboot.base.BaseService;
import com.springboot.domain.MenuCategory;

public interface MenuCategoryService extends BaseService<MenuCategory>{

	public void updateModules(Map<String, Object> map);
}
