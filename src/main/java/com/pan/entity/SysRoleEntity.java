package com.pan.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统角色表
 * @author tangpan
 */
@Table(name = "sys_role")
public class SysRoleEntity extends BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1l;
    /** 角色名称 */
    @Column(name = "role_name")
    private String roleName;
    /** 备注 */
    @Column(name = "remark")
    private String remark;
    /** 创建人 */
    @Column(name = "create_user_id")
    private long createUserId;
    /** 创建时间 */
    @Column(name = "create_time")
    private Date createTime;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
