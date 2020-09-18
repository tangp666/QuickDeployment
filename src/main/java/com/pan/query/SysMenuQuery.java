package com.pan.query;

import com.pan.entity.SysMenuEntity;

import java.util.List;

/**
 * 系统菜单
 * @author tangpan
 */
public class SysMenuQuery extends SysMenuEntity {

    /* 子节点菜单 */
    private List<SysMenuQuery> childMenu;

    public List<SysMenuQuery> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<SysMenuQuery> childMenu) {
        this.childMenu = childMenu;
    }
}
