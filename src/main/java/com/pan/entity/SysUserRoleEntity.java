package com.pan.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户角色关联关系
 * @author tangpan
 */
@Table(name = "sys_user_role")
public class SysUserRoleEntity extends BaseEntity implements Serializable {

    private static final long serializable = 1l;

    //用户id
    @Column(name = "user_id")
    private long userId;

    //角色id
    @Column(name = "role_id")
    private long roleId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
