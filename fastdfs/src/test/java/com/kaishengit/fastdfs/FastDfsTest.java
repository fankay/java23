package com.kaishengit.fastdfs;

import com.kaishengit.util.fastdfs.FastDfsResult;
import com.kaishengit.util.fastdfs.FastDfsUtil;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FastDfsTest {

    @Test
    public void uploadFile() throws IOException, MyException {
        //1.配置Tracker的地址
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.135.200:22122");
        ClientGlobal.initByProperties(properties);

        //2.获取Tracker的服务器
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();

        //3.获取StorageClient
        StorageClient storageClient = new StorageClient(trackerServer,null);

        FileInputStream fileInputStream = new FileInputStream("D:/temp/upload/1.png");

        NameValuePair[] nameValuePairs = new NameValuePair[3];
        //设置文件的MetaData(元信息)
        nameValuePairs[0] = new NameValuePair("width","100");
        nameValuePairs[1] = new NameValuePair("height","200");
        nameValuePairs[2] = new NameValuePair("location","beijing");

        String[] result = storageClient.upload_file(IOUtils.toByteArray(fileInputStream),"png",nameValuePairs);
        System.out.println("GroupName: " + result[0]);
        System.out.println("FilePath: " + result[1]);

    }

    @Test
    public void uploadImage() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:/temp/upload/2.png");
        FastDfsUtil fastDfsUtil = new FastDfsUtil();
        FastDfsResult result = fastDfsUtil.updateFile(IOUtils.toByteArray(fileInputStream),"png");
        System.out.println(result.getAbsolutePath());
    }

    @Test
    public void downloadFile() throws IOException {
        FastDfsUtil fastDfsUtil = new FastDfsUtil();
        byte[] bytes =  fastDfsUtil.downloadFile("group1","M00/00/00/wKiHyFmOwqGAfyyjAAV7vUcWNoM159.png");
        FileOutputStream fileOutputStream = new FileOutputStream("D:/a.png");
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    @Test
    public void getFileInfo() {
        FastDfsUtil fastDfsUtil = new FastDfsUtil();
        FileInfo fileInfo = fastDfsUtil.getFileInfo("group1","M00/00/00/wKiHyFmOwqGAfyyjAAV7vUcWNoM159.png");
        System.out.println(fileInfo.getSourceIpAddr());
        System.out.println(fileInfo.getCreateTimestamp());
        System.out.println(fileInfo.getFileSize());
        System.out.println(fileInfo.getCrc32());
    }

    @Test
    public void getMetaData() {
        FastDfsUtil fastDfsUtil = new FastDfsUtil();
        NameValuePair[] nameValuePairs = fastDfsUtil.getFileMetaData("group1","M00/00/00/wKiHyFmOxdeAa4fmAAV7vUcWNoM726.png");
        for(NameValuePair valuePair : nameValuePairs) {
            System.out.println(valuePair.getName() + " -> " + valuePair.getValue());
        }
    }

    @Test
    public void deleteFile() {
        FastDfsUtil fastDfsUtil = new FastDfsUtil();
        fastDfsUtil.deleteFile("group1","M00/00/00/wKiHyFmOxdeAa4fmAAV7vUcWNoM726.png");
    }



}
