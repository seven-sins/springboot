package com.springboot.controller.sys;

import com.springboot.base.BaseController;
import com.springboot.po.Privilege;
import com.springboot.service.sys.RolePrivilegeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author seven sins
 * @date 2017年5月8日 下午10:59:59
 */
@RestController
public class RolePrivilegeController extends BaseController{
	
	@Autowired
	RolePrivilegeService rolePrivilegeService;
	
	/**
	 * 根据角色Id获取权限列表
	 * @param roleId
	 * @return
	 */
	@GetMapping("/role/privilege/{roleId}")
	public Object list(@PathVariable("roleId") int roleId){
		List<Privilege> dataList = rolePrivilegeService.findByRoleId(roleId);
		
		return super.resultMap(0, dataList);
	}

	@GetMapping("/rolePrivilege/{roleId}")
	public Object get(@PathVariable("roleId") int roleId){
		List<Integer> ids = rolePrivilegeService.getPrivilegeByRoleId(roleId);

		return super.resultMap(0, ids);
	}
	
	@PutMapping("/rolePrivilege/{roleId}")
	public Object create(@RequestBody Map<String, Object> map, @PathVariable("roleId") int roleId){
		rolePrivilegeService.update(roleId, map);
		
		return super.resultMsg(0, "操作成功");
	}
	
}
