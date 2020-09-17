package com.pan.util;

import ch.ethz.ssh2.*;
import com.jcraft.jsch.*;
import com.pan.entity.ResultEntity;
import com.pan.entity.ScpConnectEntity;
import com.pan.enums.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 文件上传
 * @author tangpan
 */
public class UpLoadUtils {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(UpLoadUtils.class);

    /**
     * 文件上传 指定服务器
     * @param file 文件
     * @param targetPath 目标路径
     * @param url 服务器url
     * @param userName 服务器用户名
     * @param passWord 服务器密码
     * @param remoteFileName 服务器新建文件名
     */
    public static ResultEntity upLoadFiles(File file, String targetPath, String url,
                                           String userName, String passWord, String remoteFileName){
        //服务器信息
        ScpConnectEntity scpConnectEntity = new ScpConnectEntity();
        scpConnectEntity.setUrl(url);
        scpConnectEntity.setUserName(userName);
        scpConnectEntity.setPassWord(passWord);
        scpConnectEntity.setTargetPath(targetPath);
        //返回信息
        int code = -1;
        String message = "";
        try {
            if(file == null && !file.exists()){
                throw new IllegalArgumentException("请确保上传文件不为空且存在");
            }
            if(remoteFileName == null || "".equals(remoteFileName.trim())){
                throw new IllegalArgumentException("远程服务器新建文件名不能为空");
            }
            code = ResultEnum.SUCCESS.getCode();
            message = ResultEnum.SUCCESS.getMessage();
            remoteUploadFile(scpConnectEntity, file, remoteFileName);
        } catch (IOException e) {
            code = ResultEnum.EXCEPTION.getCode();
            message = e.getMessage();
            e.printStackTrace();
        }
        return new ResultEntity(code, message);
    }

    /**
     * 上传文件至服务器端
     * @param scpConnectEntity 服务器信息
     * @param file 上传文件
     * @param remoteFileName 服务器新建文件名
     */
    private static void remoteUploadFile(ScpConnectEntity scpConnectEntity, File file, String remoteFileName) throws IOException {

        Connection connection = null;
        ch.ethz.ssh2.Session session = null;
        SCPOutputStream scpOutputStream = null;
        FileInputStream fileInputStream = null;
        //指定到目录下
        try {
            createDir(scpConnectEntity);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        try {
            //创建连接
            connection = new Connection(scpConnectEntity.getUrl());
            connection.connect();
            if(connection.authenticateWithPassword(scpConnectEntity.getUserName(), scpConnectEntity.getPassWord())){
                throw new RuntimeException("SSH连接服务器失败");
            }
            //创建连接
            session = connection.openSession();
            SCPClient scpClient = connection.createSCPClient();
            //创建文件流
            scpOutputStream = scpClient.put(remoteFileName, file.length(), scpConnectEntity.getTargetPath(), "0666");

            fileInputStream = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int hasMore = fileInputStream.read(buf);

            while(hasMore != -1){
                scpOutputStream.write(buf);
                hasMore = fileInputStream.read(buf);
            }
            scpOutputStream.flush();
        } catch (Exception e) {
            throw new IOException("SSH上传文件至服务器出错"+e.getMessage());
        } finally {
            if(null != fileInputStream){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != scpOutputStream){
                try {
                    scpOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != session){
                session.close();
            }
            if(null != connection){
                connection.close();
            }
        }
    }

    /**
     * 服务器创建目录 并指定到当前目录下
     * @param scpConnectEntity
     * @return
     * @throws JSchException
     */
    private static boolean createDir(ScpConnectEntity scpConnectEntity) throws JSchException{
        JSch jSch = new JSch();
        com.jcraft.jsch.Session sshSession = null;
        Channel channel = null;
        try {
            //创建ssh连接
            sshSession = jSch.getSession(scpConnectEntity.getUserName(), scpConnectEntity.getUrl(), 22);
            sshSession.setPassword(scpConnectEntity.getPassWord());
            sshSession.setConfig("StrictHostKeyChecking", "no");
            sshSession.connect();
            channel = sshSession.openChannel("sftp");
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            throw new JSchException("SFTP连接服务器失败"+e.getMessage());
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


}
