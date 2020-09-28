package com.pan.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 服务器信息
 * @author tangpan
 */
@Table(name = "t_server_info")
public class TServerInfoEntity extends BaseEntity implements Serializable {

    private static final long Serializable = 1l;

    /* 服务器名称 */
    @Column(name = "server_name")
    private String serverName;
    /* 服务器描述 */
    @Column(name = "server_desc")
    private String serverDesc;
    /* 服务器地址 127.0.0.1 */
    @Column(name = "server_address")
    private String serverAddress;
    /* 服务器端口号  22*/
    @Column(name = "server_prot")
    private int serverProt;
    /* 服务器账号 */
    @Column(name = "server_account")
    private String serverAccount;
    /* 服务器密码 */
    @Column(name = "server_password")
    private String serverPassword;
    /* 创建人 */
    @Column(name = "server_password")
    private long create_user_id;
    /* 创建时间 */
    @Column(name = "server_password")
    private Date create_time;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerDesc() {
        return serverDesc;
    }

    public void setServerDesc(String serverDesc) {
        this.serverDesc = serverDesc;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerProt() {
        return serverProt;
    }

    public void setServerProt(int serverProt) {
        this.serverProt = serverProt;
    }

    public String getServerAccount() {
        return serverAccount;
    }

    public void setServerAccount(String serverAccount) {
        this.serverAccount = serverAccount;
    }

    public String getServerPassword() {
        return serverPassword;
    }

    public void setServerPassword(String serverPassword) {
        this.serverPassword = serverPassword;
    }

    public long getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(long create_user_id) {
        this.create_user_id = create_user_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
