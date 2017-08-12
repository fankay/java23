package com.kaishengit.util.fastdfs;

public class FastDfsResult {

    private String groupName;
    private String filePath;

    public FastDfsResult(){}

    public FastDfsResult(String groupName, String filePath) {
        this.groupName = groupName;
        this.filePath = filePath;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAbsolutePath() {
        return "/"+getGroupName()+"/"+getFilePath();
    }
}
