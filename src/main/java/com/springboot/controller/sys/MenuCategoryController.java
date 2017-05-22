package com.springboot.controller.sys;

import com.springboot.base.BaseController;
import com.springboot.config.annotation.Valid;
import com.springboot.po.MenuCategory;
import com.springboot.service.sys.MenuCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author seven sins
 * @date 2017年5月8日 下午10:59:40
 */
@RequestMapping("/api")
@RestController
public class MenuCategoryController extends BaseController{
	
	@Autowired
	MenuCategoryService menuCategoryService;
	
	@PutMapping("/menuCategory/{id}/modules")
	public Object updateModules(@RequestBody Map<String, Object> map, @PathVariable("id") int id){
		map.put("id", id);
		menuCategoryService.updateModules(map);
		
		return SUCCESS;
	}

	@GetMapping("/menuCategory")
	public Object index(MenuCategory menuCategory){
		List<MenuCategory> dataList = menuCategoryService.find(menuCategory);
		int total = menuCategoryService.count(menuCategory);
		
		return super.resultMap(200, dataList, total);
	}

	@GetMapping("/menuCategory/{id}")
	public Object get(@PathVariable("id") int id){
		MenuCategory menuCategory = menuCategoryService.get(id);

		return super.resultMap(200, menuCategory);
	}
	
	@PostMapping("/menuCategory")
	public Object create(@Valid @RequestBody MenuCategory menuCategory){
		menuCategory.setParentId(-1);
		menuCategory.setType(0); // 0:菜单分类，1:模块，2:接口
		menuCategoryService.insert(menuCategory);
		
		return SUCCESS;
	}
	
	@PutMapping("/menuCategory/{id}")
	public Object update(@RequestBody MenuCategory menuCategory, @PathVariable("id") int id){
		menuCategory.setId(id);
		menuCategoryService.update(menuCategory);
		
		return SUCCESS;
	}
	
	@DeleteMapping("/menuCategory/{id}")
	public Object remove(@PathVariable("id") Integer id){
		menuCategoryService.deleteById(id);
		
		return SUCCESS;
	}
	
}
