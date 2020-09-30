package com.pan.thread;

import com.pan.entity.TServerInfoEntity;
import com.pan.util.UpLoadUtils;

import java.io.File;

/**
 * 服务器上传文件线程任务
 * @author tangpan
 */
public class UploadServerTask implements Runnable {

    //上传的文件
    private File file;
    //文件名
    private String sourceCodeName;
    //服务器信息
    private TServerInfoEntity tServerInfoEntity;
    //构造
    public UploadServerTask(File file, String sourceCodeName, TServerInfoEntity tServerInfoEntity) {
        this.file = file;
        this.sourceCodeName = sourceCodeName;
        this.tServerInfoEntity = tServerInfoEntity;
    }

    @Override
    public void run() {
        UpLoadUtils.upLoadFiles(file, tServerInfoEntity.getServerAddress(), tServerInfoEntity.getServerAddress(), tServerInfoEntity.getServerName(), tServerInfoEntity.getServerPassword(), tServerInfoEntity.getServerProt(), sourceCodeName);
    }
}
