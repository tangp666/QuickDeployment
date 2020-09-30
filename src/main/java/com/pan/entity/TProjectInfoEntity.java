package com.pan.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 服务器信息
 * @author tangpan
 */
@Table(name = "t_project_info")
public class TProjectInfoEntity extends BaseEntity implements Serializable {

    private static final long serializable = 1l;

    /* 项目名称 */
    @Column(name = "project_name")
    private String projectName;
    /* 项目源码位置 */
    @Column(name = "project_source_code_url")
    private String projectSourceCodeUrl;
    /* 项目描述 */
    @Column(name = "project_desc")
    private String projectDesc;
    /* 创建人 */
    @Column(name = "create_user_id")
    private long createUserId;
    /* 创建时间 */
    @Column(name = "create_time")
    private Date createTime;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectSourceCodeUrl() {
        return projectSourceCodeUrl;
    }

    public void setProjectSourceCodeUrl(String projectSourceCodeUrl) {
        this.projectSourceCodeUrl = projectSourceCodeUrl;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
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
