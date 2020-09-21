package com.pan.service;

import com.pan.entity.SysRoleMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 菜单角色管理
 * @author tangpan
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenuEntity> {

    /**
     * 批量插入
     * @param entities
     * @return
     */
    int batchInsert(List<SysRoleMenuEntity> entities);

    /**
     * 根据条件删除
     * @param map
     * @return
     */
    int deleteByParames(Map<String,Object> map);

}
