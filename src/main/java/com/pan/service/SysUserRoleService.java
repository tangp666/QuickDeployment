package com.pan.service;

import com.pan.entity.SysUserRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户角色信息
 * @author tangpan
 */
public interface SysUserRoleService extends BaseService<SysUserRoleEntity> {

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(List<SysUserRoleEntity> list);

    /**
     * 根据参数删除
     * @param map
     * @return
     */
    int deleteParame(Map<String,Object> map);

}
