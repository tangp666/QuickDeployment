package com.pan.query;

import com.pan.entity.TServerInfoEntity;

/**
 * 服务器展开列表query
 * @author tangpan
 */
public class TServerInfoQuery extends TServerInfoEntity {

    /* 文件名 */
    private String fileName;
    /* 文件路径 */
    private String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
