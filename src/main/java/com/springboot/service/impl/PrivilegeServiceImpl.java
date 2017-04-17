package com.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.springboot.domain.Privilege;
import com.springboot.mapper.PrivilegeMapper;
import com.springboot.service.PrivilegeService;
import com.springboot.service.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by seven sins on 2/1/2017.
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    PrivilegeMapper privilegeMapper;
    @Autowired
    RedisService redisService;

    @Override
    public List<Privilege> find(Privilege entity) {
        PageHelper.startPage(entity.getIndex(), entity.getSize());
        return privilegeMapper.find(entity);
    }

    @Override
    public int count(Privilege entity) {
        return privilegeMapper.count(entity);
    }

    @Override
    public Privilege get(Serializable id) {
        return privilegeMapper.get(id);
    }

    @Override
    public void insert(Privilege entity) {
        privilegeMapper.insert(entity);
        redisService.delete("privilege-all"); // 删除缓存
    }

    @Override
    public void update(Privilege entity) {
        privilegeMapper.update(entity);
        redisService.delete("privilege-all"); // 删除缓存
    }

    @Override
    public void deleteById(Serializable id) {
        privilegeMapper.deleteById(id);
        redisService.delete("privilege-all"); // 删除缓存
    }

    @Override
    public void delete(Serializable[] ids) {

    }

    /**
     * 不获取rest接口
     * @param privilege
     * @return
     */
    @Override
    public List<Privilege> list(Privilege privilege) {
        return privilegeMapper.list(privilege);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Privilege> getAll(Privilege privilege) {
		Object redisObj = redisService.get("privilege-all");
		if(redisObj != null){
			return (List<Privilege>) redisObj;
		}
		List<Privilege> list = privilegeMapper.getAll(privilege);
		redisService.add("privilege-all", list);
		
		return list;
	}
}
