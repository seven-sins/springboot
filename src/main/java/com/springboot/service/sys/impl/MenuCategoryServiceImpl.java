package com.springboot.service.sys.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.config.exception.SevenException;
import com.springboot.mapper.sys.MenuCategoryMapper;
import com.springboot.po.MenuCategory;
import com.springboot.service.assist.RedisService;
import com.springboot.service.sys.MenuCategoryService;

@Service
public class MenuCategoryServiceImpl implements MenuCategoryService{

	@Autowired
	MenuCategoryMapper menuCategoryMapper;
	@Autowired
	RedisService redisService;
	
	@Override
	public List<MenuCategory> find(MenuCategory entity) {
		return menuCategoryMapper.find(entity);
	}

	@Override
	public int count(MenuCategory entity) {
		return menuCategoryMapper.count(entity);
	}

	@Override
	public MenuCategory get(Serializable id) {
		return menuCategoryMapper.get(id);
	}

	@Override
	public void insert(MenuCategory entity) {
		menuCategoryMapper.insert(entity);		
		redisService.delete("privilege-all"); // 删除缓存
	}

	@Override
	public void update(MenuCategory entity) {
		menuCategoryMapper.update(entity);		
		redisService.delete("privilege-all"); // 删除缓存
	}

	@Override
	public void deleteById(Serializable id) {
		menuCategoryMapper.deleteById(id);		
		redisService.delete("privilege-all"); // 删除缓存
	}

	@Override
	public void delete(Serializable[] ids) {
		menuCategoryMapper.delete(ids);		
		redisService.delete("privilege-all"); // 删除缓存
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateModules(Map<String, Object> map) {
		menuCategoryMapper.removeModules(Integer.valueOf(map.get("id").toString()));
		redisService.delete("privilege-all"); // 删除缓存
		Object object = map.get("moduleIds");
		if(object == null){
			throw new SevenException(1, "获取模块数据出错");
		}
		List<Integer> ids = (List<Integer>) object;
		if(!ids.isEmpty()){
			menuCategoryMapper.updateModules(map);
		}
	}
}
