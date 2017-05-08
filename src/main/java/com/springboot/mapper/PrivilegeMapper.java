package com.springboot.mapper;

import com.springboot.base.BaseMapper;
import com.springboot.domain.Privilege;

import java.util.List;

/**
 * @author seven sins
 * @date 2017年5月8日 下午11:01:14
 */
public interface PrivilegeMapper extends BaseMapper<Privilege>{ //

    public List<Privilege> list(Privilege privilege);
    
    /**
     * 获取所有状态为启用的数据
     * @param privilege
     * @return
     */
    public List<Privilege> getAll(Privilege privilege);
}
