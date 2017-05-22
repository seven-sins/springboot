package com.springboot.controller.sys;

import com.springboot.base.BaseController;
import com.springboot.config.annotation.Valid;
import com.springboot.po.Privilege;
import com.springboot.service.sys.PrivilegeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author seven sins
 * @date 2017年5月8日 下午10:59:47
 */
@RestController
public class PrivilegeController extends BaseController{
	
	@Autowired
	PrivilegeService privilegeService;
	
	/**
	 * 获取所有status=0的数据
	 * @param privilege
	 * @return
	 */
	@GetMapping("/privilege/all")
	public Object getAll(){
		Privilege privilege = new Privilege();
		privilege.setStatus(0);
		privilege.setType(null);
		List<Privilege> dataList = privilegeService.getAll(privilege);

		return super.resultMap(0, dataList);
	}

	/**
	 * 仅获取模块数据 type=0
	 * @param privilege
	 * @return
	 */
	@GetMapping("/privilege/list")
	public Object list(Privilege privilege){
		List<Privilege> dataList = privilegeService.list(privilege);

		return super.resultMap(0, dataList);
	}

	/**
	 * 
	 * @param privilege
	 * @return
	 */
	@GetMapping("/privilege")
	public Object index(Privilege privilege){
		List<Privilege> dataList = privilegeService.find(privilege);
		int total = privilegeService.count(privilege);
		
		return super.resultMap(0, dataList, total);
	}

	@GetMapping("/privilege/{id}")
	public Object get(@PathVariable("id") int id){
		Privilege privilege = privilegeService.get(id);

		return super.resultMap(0, privilege);
	}
	
	@PostMapping("/privilege")
	public Object create(@Valid @RequestBody Privilege privilege){
		privilegeService.insert(privilege);
		
		return super.resultMsg(0, "操作成功");
	}
	
	@PutMapping("/privilege/{id}")
	public Object update(@RequestBody Privilege privilege, @PathVariable("id") int id){
		privilege.setId(id);
		privilegeService.update(privilege);
		
		return super.resultMsg(0, "操作成功");
	}
	
	@DeleteMapping("/privilege/{id}")
	public Object remove(@PathVariable("id") int id){
		privilegeService.deleteById(id);
		
		return super.resultMsg(0, "操作成功");
	}
	
}
