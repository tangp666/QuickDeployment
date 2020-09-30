package com.pan.query;

import com.pan.entity.TProjectFilePathInfoEntity;

/**
 * 路径query
 * @author tangpan
 */
public class TProjectFilePathInfoQuery extends TProjectFilePathInfoEntity {

    /* 服务器名称 */
    private String serverName;
    /* 服务器地址 */
    private String serverAddress;
    /* 项目名称 */
    private String projectName;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
