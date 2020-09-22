package com.pan.service;

import com.pan.entity.SysRoleEntity;

import java.util.Map;

/**
 * 角色管理
 * @author tangpan
 */
public interface SysRoleService extends BaseService<SysRoleEntity>{

    /**
     * 判断是否是管理员
     * @param map
     * @return
     */
    int checkAdmin(Map<String,Object> map);

}
