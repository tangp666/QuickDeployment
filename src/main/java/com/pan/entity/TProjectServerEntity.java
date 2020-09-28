package com.pan.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 项目和服务器关联关系
 * @author tangpan
 */
@Table(name = "t_project_server")
public class TProjectServerEntity extends BaseEntity implements Serializable {

    private static final long serializable = 1l;

    /* 项目主键 */
    @Column(name = "project_id")
    private long projectId;

    /* 服务器主键 */
    @Column(name = "server_id")
    private long serverId;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }
}
