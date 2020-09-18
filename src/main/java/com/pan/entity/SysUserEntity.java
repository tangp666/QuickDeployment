package com.pan.entity;


import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @author tangpan
 */
@Table(name = "sys_user")
public class SysUserEntity extends BaseEntity implements Serializable {

    /* 用户名 */
    @Column(name = "username")
    private String username;
    /* 密码 */
    @Column(name = "password")
    private String password;
    /* 加盐密码 随机生成 */
    @Column(name = "salt")
    private String salt;
    /* 账号状态 */
    @Column(name = "status")
    private Integer status;
    /* 创建时间 */
    @Column(name = "create_time")
    private Date createTime;
    /* 创建用户 */
    @Column(name = "create_user_id")
    private long createUserId;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }


}