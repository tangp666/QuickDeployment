package com.pan.entity;

/**
 * 文件上传封装服务器信息
 * @author tangpan
 */
public class ScpConnectEntity {

    /* 服务器用户名 */
    private String userName;
    /* 服务器密码 */
    private String passWord;
    /* 服务器url */
    private String url;
    /* 目标路径 */
    private String targetPath;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }
}
