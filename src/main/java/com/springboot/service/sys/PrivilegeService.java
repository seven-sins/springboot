package com.springboot.service.sys;

import com.springboot.base.BaseService;
import com.springboot.po.Privilege;

import java.util.List;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:01:51
 */
public interface PrivilegeService extends BaseService<Privilege>{

    public List<Privilege> list(Privilege privilege);
    
    /**
     * 获取所有数据
     * @param privilege
     * @return
     */
    public List<Privilege> getAll(Privilege privilege);
}
