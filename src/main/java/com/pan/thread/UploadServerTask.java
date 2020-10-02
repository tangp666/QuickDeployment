package com.pan.thread;

import com.pan.query.TServerInfoQuery;
import com.pan.util.UpLoadUtils;

import java.io.File;

/**
 * 服务器上传文件线程任务
 * @author tangpan
 */
public class UploadServerTask implements Runnable {

    //上传的文件
    private String fileUrl;
    //服务器信息
    private TServerInfoQuery tServerInfoQuery;
    //构造
    public UploadServerTask(String fileUrl, TServerInfoQuery tServerInfoQuery) {
        this.fileUrl = fileUrl;
        this.tServerInfoQuery = tServerInfoQuery;
    }

    @Override
    public void run() {
        /**
         * file jar 文件
         *  服务器路径
         *  服务器地址
         *  服务器账号
         *  服务器密码
         *  服务器端口
         *  服务器文件名
         */
        UpLoadUtils.upLoadFiles(fileUrl, tServerInfoQuery.getFilePath(), tServerInfoQuery.getServerAddress(), tServerInfoQuery.getServerAccount(), tServerInfoQuery.getServerPassword(), tServerInfoQuery.getServerProt(), tServerInfoQuery.getFileName());
    }
}
