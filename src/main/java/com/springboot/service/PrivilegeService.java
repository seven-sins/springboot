package com.springboot.service;

import com.springboot.base.BaseService;
import com.springboot.domain.Privilege;

import java.util.List;

public interface PrivilegeService extends BaseService<Privilege>{

    public List<Privilege> list(Privilege privilege);
    
    /**
     * 获取所有数据
     * @param privilege
     * @return
     */
    public List<Privilege> getAll(Privilege privilege);
}
