package com.springboot.controller.sys;

import com.springboot.base.BaseController;
import com.springboot.config.annotation.Valid;
import com.springboot.po.Role;
import com.springboot.service.sys.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author seven sins
 * @date 2017年5月8日 下午10:59:53
 */
@RequestMapping("/api")
@RestController
public class RoleController extends BaseController{
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("/role")
	public Object list(Role role){
		List<Role> dataList = roleService.find(role);
		int total = roleService.count(role);
		
		return super.resultMap(200, dataList, total);
	}

	@GetMapping("/role/{id}")
	public Object get(@PathVariable("id") int id){
		Role role = roleService.get(id);

		return super.resultMap(200, role);
	}
	
	@PostMapping("/role")
	public Object create(@Valid @RequestBody Role role){
		roleService.insert(role);
		
		return SUCCESS;
	}
	
	@PutMapping("/role/{id}")
	public Object update(@RequestBody Role role, @PathVariable("id") int id){
		role.setId(id);
		roleService.update(role);
		
		return SUCCESS;
	}
	
	@DeleteMapping("/role/{id}")
	public Object remove(@PathVariable("id") int id){
		roleService.deleteById(id);
		
		return SUCCESS;
	}
	
}
