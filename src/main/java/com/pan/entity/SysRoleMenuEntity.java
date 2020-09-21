package com.pan.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色和菜单管理表
 * @author tangpan
 */
@Table(name = "sys_role_menu")
public class SysRoleMenuEntity extends BaseEntity implements Serializable {

    private static final long serializable = 1l;
    //角色id
    @Column(name = "role_id")
    private long roleId;
    //菜单id
    @Column(name = "menu_id")
    private long menuId;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }
}
