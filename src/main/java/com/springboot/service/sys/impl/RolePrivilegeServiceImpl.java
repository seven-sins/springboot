package com.springboot.service.sys.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.config.exception.SevenException;
import com.springboot.mapper.sys.RolePrivilegeMapper;
import com.springboot.po.Privilege;
import com.springboot.po.RolePrivilege;
import com.springboot.service.assist.RedisService;
import com.springboot.service.sys.RolePrivilegeService;

@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService {

	@Autowired
	RolePrivilegeMapper rolePrivilegeMapper;
	@Autowired
	RedisService redisService;
	
	@Override
	public List<RolePrivilege> find(RolePrivilege entity) {
		return rolePrivilegeMapper.find(entity);
	}

	@Override
	public int count(RolePrivilege entity) {
		return 0;
	}

	@Override
	public RolePrivilege get(Serializable id) {
		return null;
	}

	@Override
	public void insert(RolePrivilege entity) {
		rolePrivilegeMapper.insert(entity);		
	}

	@Override
	public void update(RolePrivilege entity) {
		
	}

	@Override
	public void deleteById(Serializable id) { // roleId
		rolePrivilegeMapper.deleteById(id);		
	}

	@Override
	public void delete(Serializable[] ids) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(int roleId, Map<String, Object> map) {
		// 先删除已设置的权限
		rolePrivilegeMapper.deleteById(roleId);
		// 删除redis中的缓存
		redisService.delete("privilege-roleId-" + roleId);
		// 设置权限
		Object object = map.get("privileges");
		if(object == null){
			throw new SevenException(1, "获取权限数据出错");
		}
		List<Integer> ids = (List<Integer>) object;
		if(!ids.isEmpty()){
			for(int i=0; i<ids.size(); i++){
				rolePrivilegeMapper.insert(new RolePrivilege(roleId, ids.get(i)));
			}
		}
	}

	@Override
	public List<Integer> getPrivilegeByRoleId(int roleId) {
		return rolePrivilegeMapper.getPrivilegeByRoleId(roleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Privilege> findByRoleId(int roleId) {
		Object object = redisService.get("privilege-roleId-" + roleId); // 从缓存中获取
		if(object != null){ // 
			return (List<Privilege>) object;
		}
		List<Privilege> list = rolePrivilegeMapper.findByRoleId(roleId);
		redisService.add("privilege-roleId-" + roleId, list);
		
		return list;
	}
}
