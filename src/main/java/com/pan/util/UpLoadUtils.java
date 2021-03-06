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
     * @param fileUrl 文件路径
     * @param targetPath 目标路径
     * @param url 服务器url
     * @param userName 服务器用户名
     * @param passWord 服务器密码
     * @param prot 服务器端口号 传入对象值为-1时默认为22
     * @param remoteFileName 服务器新建文件名
     */
    public static ResultEntity upLoadFiles(String fileUrl, String targetPath, String url,
                                           String userName, String passWord, int prot, String remoteFileName){
        //服务器信息
        ScpConnectEntity scpConnectEntity = new ScpConnectEntity();
        scpConnectEntity.setUrl(url);
        scpConnectEntity.setUserName(userName);
        scpConnectEntity.setPassWord(passWord);
        scpConnectEntity.setTargetPath(targetPath);
        scpConnectEntity.setProt(prot == -1 ? 22 : prot);
        //返回信息
        int code = -1;
        String message = "";
        try {
            if(new File(fileUrl) == null && !new File(fileUrl).exists()){
                throw new IllegalArgumentException("请确保上传文件不为空且存在");
            }
            if(remoteFileName == null || "".equals(remoteFileName.trim())){
                throw new IllegalArgumentException("远程服务器新建文件名不能为空");
            }
            code = ResultEnum.SUCCESS.getCode();
            message = ResultEnum.SUCCESS.getMessage();
            remoteUploadFile(scpConnectEntity, fileUrl, remoteFileName);
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
     * @param fileUrl 上传文件路径
     * @param remoteFileName 服务器新建文件名
     */
    private static void remoteUploadFile(ScpConnectEntity scpConnectEntity, String fileUrl, String remoteFileName) throws IOException {

//        Connection connection = null;
//        ch.ethz.ssh2.Session session = null;
//        SCPOutputStream scpOutputStream = null;
//        FileInputStream fileInputStream = null;
        //指定到目录下  并上传文件
        try {
            JSchUtils.createDir(scpConnectEntity, fileUrl, remoteFileName);
        } catch (JSchException e) {
            e.printStackTrace();
        }
//        try {
//            //创建连接
//            connection = new Connection(scpConnectEntity.getUrl());
//            connection.connect();
//            if(connection.authenticateWithPassword(scpConnectEntity.getUserName(), scpConnectEntity.getPassWord())){
//                throw new RuntimeException("SSH连接服务器失败");
//            }
//            //创建连接
//            session = connection.openSession();
//            SCPClient scpClient = connection.createSCPClient();
//            //创建文件流
//            scpOutputStream = scpClient.put(remoteFileName, file.length(), scpConnectEntity.getTargetPath(), "0666");
//
//            fileInputStream = new FileInputStream(file);
//            byte[] buf = new byte[1024];
//            int hasMore = fileInputStream.read(buf);
//
//            while(hasMore != -1){
//                scpOutputStream.write(buf);
//                hasMore = fileInputStream.read(buf);
//            }
//            scpOutputStream.flush();
//        } catch (Exception e) {
//            throw new IOException("SSH上传文件至服务器出错"+e.getMessage());
//        } finally {
//            if(null != fileInputStream){
//                try {
//                    fileInputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(null != scpOutputStream){
//                try {
//                    scpOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(null != session){
//                session.close();
//            }
//            if(null != connection){
//                connection.close();
//            }
//        }
    }

}
