package com.pan.service;

import com.pan.entity.SysMenuEntity;
import com.pan.query.SysMenuQuery;

import java.util.List;

/**
 * 系统菜单管理
 * @author tangpan
 */
public interface SysMenuService {

    /**
     *  获取所有的系统菜单
     */
    List<SysMenuEntity> findAllMenu();

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

}
