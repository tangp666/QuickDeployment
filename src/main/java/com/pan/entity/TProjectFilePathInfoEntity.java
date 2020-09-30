package com.pan.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 服务器文件路径信息
 * @author tangpan
 */
@Table(name = "t_project_file_path_info")
public class TProjectFilePathInfoEntity extends BaseEntity implements Serializable {

    private static final long Serializable = 1l;

    /* 文件路径 */
    @Column(name = "file_path")
    private String filePath;
    /* 文件名称 */
    @Column(name = "file_name")
    private String fileName;
    /* 文件类型 0：jar */
    @Column(name = "file_type")
    private long fileType;
    /* 创建人 */
    @Column(name = "create_user_id")
    private long createUserId;
    /* 创建时间 */
    @Column(name = "create_time")
    private Date createTime;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileType() {
        return fileType;
    }

    public void setFileType(long fileType) {
        this.fileType = fileType;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
