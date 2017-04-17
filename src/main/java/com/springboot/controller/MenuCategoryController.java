package com.springboot.controller;

import com.springboot.base.BaseController;
import com.springboot.config.annotation.Valid;
import com.springboot.domain.MenuCategory;
import com.springboot.service.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author seven sins
 *
 */
@RestController
public class MenuCategoryController extends BaseController{
	
	@Autowired
	MenuCategoryService menuCategoryService;
	
	@PutMapping("/menuCategory/{id}/modules")
	public Object updateModules(@RequestBody Map<String, Object> map, @PathVariable("id") int id){
		map.put("id", id);
		menuCategoryService.updateModules(map);
		
		return super.resultMsg(0, "操作成功");
	}

	@GetMapping("/menuCategory")
	public Object index(MenuCategory menuCategory){
		List<MenuCategory> dataList = menuCategoryService.find(menuCategory);
		int total = menuCategoryService.count(menuCategory);
		
		return super.resultMap(0, dataList, total);
	}

	@GetMapping("/menuCategory/{id}")
	public Object get(@PathVariable("id") int id){
		MenuCategory menuCategory = menuCategoryService.get(id);

		return super.resultMap(0, menuCategory);
	}
	
	@PostMapping("/menuCategory")
	public Object create(@Valid @RequestBody MenuCategory menuCategory){
		menuCategory.setParentId(-1);
		menuCategory.setType(0); // 0:菜单分类，1:模块，2:接口
		menuCategoryService.insert(menuCategory);
		
		return super.resultMsg(0, "操作成功");
	}
	
	@PutMapping("/menuCategory/{id}")
	public Object update(@RequestBody MenuCategory menuCategory, @PathVariable("id") int id){
		menuCategory.setId(id);
		menuCategoryService.update(menuCategory);
		
		return super.resultMsg(0, "操作成功");
	}
	
	@DeleteMapping("/menuCategory/{id}")
	public Object remove(@PathVariable("id") Integer id){
		menuCategoryService.deleteById(id);
		
		return super.resultMsg(0, "操作成功");
	}
	
}
