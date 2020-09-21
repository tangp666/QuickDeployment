package com.pan.service;

import com.pan.entity.SysMenuEntity;
import com.pan.query.SysMenuQuery;
import com.pan.query.Tree;

import java.util.List;

/**
 * 系统菜单管理
 * @author tangpan
 */
public interface SysMenuService extends BaseService<SysMenuEntity>{

    /**
     * 通过用户获取系统菜单
     * @param userId 用户id
     * @return
     */
    List<SysMenuEntity> findMenuByUser(long userId);

    /**
     * 通过用户信息获取系统菜单树结构
     * @param userId 用户id
     * @param menuType 菜单类型  0,1,2 表示菜单和按钮  如果为空或为null对象查询所有
     * @return
     */
    List<SysMenuQuery> findMenuTreeByUser(long userId, List<Integer> menuType);

    /**
     * 获取菜单树结构
     * @return
     */
    Tree<SysMenuEntity> getTree();

    /**
     * 获取菜单树结构 通过角色控制选中状态
     * @param roleId 角色id
     * @return
     */
    Tree<SysMenuEntity> getTree(long roleId);

}
