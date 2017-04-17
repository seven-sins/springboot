package com.springboot.controller;

import com.springboot.base.BaseController;
import com.springboot.config.annotation.Valid;
import com.springboot.domain.Role;
import com.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 * @author seven sins
 *
 */
@RestController
public class RoleController extends BaseController{
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("/role")
	public Object list(Role role){
		List<Role> dataList = roleService.find(role);
		int total = roleService.count(role);
		
		return super.resultMap(0, dataList, total);
	}

	@GetMapping("/role/{id}")
	public Object get(@PathVariable("id") int id){
		Role role = roleService.get(id);

		return super.resultMap(0, role);
	}
	
	@PostMapping("/role")
	public Object create(@Valid @RequestBody Role role){
		roleService.insert(role);
		
		return super.resultMsg(0, "操作成功");
	}
	
	@PutMapping("/role/{id}")
	public Object update(@RequestBody Role role, @PathVariable("id") int id){
		role.setId(id);
		roleService.update(role);
		
		return super.resultMsg(0, "操作成功");
	}
	
	@DeleteMapping("/role/{id}")
	public Object remove(@PathVariable("id") int id){
		roleService.deleteById(id);
		
		return super.resultMsg(0, "操作成功");
	}
	
}
