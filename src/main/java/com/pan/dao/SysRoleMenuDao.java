package com.pan.dao;

import com.pan.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 角色菜单关联表
 * @author tangpan
 */
@Mapper
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity>{

    /**
     * 批量插入
     * @param entities 集合
     * @return
     */
    int batchInsert(@Param("entities") List<SysRoleMenuEntity> entities);

    /**
     * 通过条件查询所有的ids
     * @param map
     * @return
     */
    List<Long> findMenusByParames(Map<String,Object> map);

    /**
     * 根据条件删除
     * @param map
     * @return
     */
    int deleteByParames(Map<String,Object> map);
}
