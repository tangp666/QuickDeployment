package com.pan.dao;

import com.pan.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单栏目dao
 * @author tangpan
 */
@Mapper
public interface SysMenuDao extends BaseDao<SysMenuEntity>{
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuEntity> searchMenuByParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();
}