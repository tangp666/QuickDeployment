package com.pan.service;

import com.pan.entity.SysMenuEntity;

import java.util.List;

/**
 * 系统菜单管理
 * @author tangpan
 */
public interface MenuService {

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

}
