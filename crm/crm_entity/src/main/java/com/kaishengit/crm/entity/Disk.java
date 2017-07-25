package com.kaishengit.crm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Disk implements Serializable {

    public static final String DISK_FOLDER_TYPE = "dir";
    public static final String DISK_FILE_TYPE = "file";

    private Integer id;

    /**
     * 文件或文件夹名称
     */
    private String name;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 父ID
     */
    private Integer pId;

    /**
     * 类型，file | dir 文件| 文件夹
     */
    private String type;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 创建人ID
     */
    private Integer accountId;

    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 加锁密码
     */
    private String password;

    /**
     * 文件存放的真实名称
     */
    private String saveName;

    /**
     * 文件的MD5值
     */
    private String md5;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}