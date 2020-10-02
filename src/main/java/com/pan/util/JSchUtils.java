package com.pan.util;

import com.jcraft.jsch.*;
import com.pan.entity.ResultEntity;
import com.pan.entity.ScpConnectEntity;
import com.pan.enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * ftp连接服务器
 * @author tangpan
 */
public class JSchUtils {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(JSchUtils.class);

    private static com.jcraft.jsch.Session sshSession = null;
    private static Channel channel = null;

    /**
     * 连接ftp服务器
     * @param scpConnectEntity 服务器信息
     * @return
     */
    public static ResultEntity jschConnect(ScpConnectEntity scpConnectEntity) throws JSchException{
        JSch jSch = new JSch();
        //返回对象
        int code = -1;
        String message = "";
        try {
            //创建ssh连接 默认端口22
            //给出连接需要的用户名，ip地址以及端口号
            sshSession = jSch.getSession(scpConnectEntity.getUserName(), scpConnectEntity.getUrl(), StringUtils.isNotEmpty(scpConnectEntity.getProt()) ? scpConnectEntity.getProt() : 22);
            //第一次登陆时候，是否需要提示信息，value可以填写 yes，no或者是ask
            sshSession.setConfig("StrictHostKeyChecking", "no");
            //设置是否超时
            sshSession.setTimeout(10000);
            //设置密码
            sshSession.setPassword(scpConnectEntity.getPassWord());
            sshSession.connect();

            channel = sshSession.openChannel("sftp");
            channel.connect();

            code = ResultEnum.SUCCESS.getCode();
            message = ResultEnum.SUCCESS.getMessage();
        } catch (JSchException e) {
            e.printStackTrace();
            code = ResultEnum.EXCEPTION.getCode();
            message = e.getMessage();
            throw new JSchException("SFTP连接服务器失败"+e.getMessage());
        }
        return new ResultEntity(code,message);
    }

    /**
     * 服务器创建目录 并指定到当前目录下
     * @param scpConnectEntity 连接参数实体
     * @param fileUrl 文件路径
     * @param remoteFileName 文件名
     * @return
     * @throws JSchException
     */
    public static boolean createDir(ScpConnectEntity scpConnectEntity, String fileUrl, String remoteFileName) throws JSchException{
        ResultEntity resultEntity = jschConnect(scpConnectEntity);
        if(MacroelementUtils.ZERO != resultEntity.getCode()){
            throw new JSchException("服务器连接失败!");
        }

        ChannelSftp channelSftp = (ChannelSftp)channel;
        if(isDirExist(scpConnectEntity.getTargetPath(), channelSftp)){
            channel.disconnect();
            channelSftp.disconnect();
            sshSession.disconnect();
            return true;
        }else {
            String[] pathArry = scpConnectEntity.getTargetPath().split("/");
            StringBuffer filePath=new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                try {
                    if (isDirExist(filePath.toString(),channelSftp)) {
                        channelSftp.cd(filePath.toString());
                    } else {
                        // 建立目录
                        channelSftp.mkdir(filePath.toString());
                        // 进入并设置为当前目录
                        channelSftp.cd(filePath.toString());
                    }
                } catch (SftpException e) {
                    e.printStackTrace();
                    throw new JSchException("SFTP无法正常操作服务器"+e.getMessage());
                }
            }
        }
        //上传文件
        try {
            channelSftp.put(fileUrl, remoteFileName, ChannelSftp.OVERWRITE);
        } catch (SftpException e) {
            e.printStackTrace();
            throw new JSchException("SFTP服务器文件上传"+e.getMessage());
        }
        //执行jar命令


        channel.disconnect();
        channelSftp.disconnect();
        sshSession.disconnect();
        return true;
    }

    /**
     * 判断服务器上是否存在文件
     * @param directory 服务器新建文件名
     * @param channelSftp 服务器连接通道
     * @return
     */
    private static boolean isDirExist(String directory, ChannelSftp channelSftp){
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = channelSftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (SftpException e) {
            if(e.getMessage().toLowerCase().equals("no such file")){
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    /**
     * 服务器上执行shell命令
     * @param command 执行的shell命令
     * @return
     */
    public static ResultEntity execCmd(String command){
        int code = -1;
        String message = "";
        try {
            //执行命令
            channel = sshSession.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            InputStream in = channel.getInputStream();
            channel.connect();
            //执行返回结果
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0)
                        break;
                    code = ResultEnum.SUCCESS.getCode();
                    message = new String(tmp, 0, i);
                }
                if (channel.isClosed()) {
                    break;
                }
            }
        } catch (JSchException e) {
            code = ResultEnum.SUCCESS.getCode();
            message = e.getMessage();
            LOGGER.error("shell执行命令异常");
            e.printStackTrace();
        } catch (IOException e){
            code = ResultEnum.SUCCESS.getCode();
            message = e.getMessage();
            LOGGER.error("执行命令返回结果异常");
            e.printStackTrace();
        } finally {
            sshSession.disconnect();
            channel.disconnect();
        }
        return new ResultEntity(code, message);
    }

}
