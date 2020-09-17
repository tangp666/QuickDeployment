package com.pan.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import com.pan.entity.ResultEntity;
import com.pan.entity.ScpConnectEntity;
import com.pan.enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;

/**
 * 执行shell命令
 * @author tangpan
 */
public class ExecCmdUtils {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ExecCmdUtils.class);

    /**
     * 执行shell命令
     * @param url 服务器地址url
     * @param userName 服务器用户名
     * @param passWord 服务器密码
     * @param prot 服务器端口号
     * @param command 执行的命令
     * @return
     */
    public static ResultEntity execCmd(String url, String userName, String passWord, int prot, String command){
        //服务器信息
        ScpConnectEntity scpConnectEntity = new ScpConnectEntity();
        scpConnectEntity.setUrl(url);
        scpConnectEntity.setUserName(userName);
        scpConnectEntity.setPassWord(passWord);
        scpConnectEntity.setProt(prot == -1 ? 22 : prot);

        int code = -1;
        String message = "";
        //创建ftp连接
        try {
            ResultEntity resultEntity = JSchUtils.jschConnect(scpConnectEntity);
            if (MacroelementUtils.ZERO != resultEntity.getCode()){
                code = ResultEnum.EXCEPTION.getCode();
                message = "连接服务器失败";
                throw new JSchException("连接服务器失败");
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return JSchUtils.execCmd(scpConnectEntity, command);
    }

}
