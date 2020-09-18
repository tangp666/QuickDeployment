package com.pan.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @author tangpan
 */
@TableName("sys_user")
public class SysUserEntity extends BaseEntity implements Serializable {

    /* 用户名 */
    private String username;
    /* 密码 */
    private String password;
    /* 加盐密码 随机生成 */
    private String salt;
    /* 账号状态 */
    private Integer status;
    /* 创建时间 */
    private Date createTime;
    /* 创建用户 */
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