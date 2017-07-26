package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.entity.DiskExample;
import com.kaishengit.crm.mapper.DiskMapper;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.exception.ServiceException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DiskServiceImpl implements DiskService {

    @Autowired
    private DiskMapper diskMapper;

    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 创建文件夹
     * @param disk
     */
    @Override
    public void saveNewFolder(Disk disk) {
        disk.setUpdateTime(new Date());
        disk.setType(Disk.DISK_FOLDER_TYPE);
        diskMapper.insert(disk);
    }

    /**
     * 根据Pid查找文件及文件夹
     * @param pId
     * @return
     */
    @Override
    public List<Disk> findDiskByPid(Integer pId) {
        DiskExample example = new DiskExample();
        example.createCriteria().andPIdEqualTo(pId);
        return diskMapper.selectByExample(example);
    }

    /**
     * 根据主键查找
     * @param id
     * @return
     */
    @Override
    public Disk findById(Integer id) {
        return diskMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存一个新的文件
     * @param disk
     * @param inputStream
     */
    @Override
    @Transactional
    public void saveNewFile(Disk disk, InputStream inputStream) {
        //重命名文件进行保存
        String fileName = disk.getName(); //a.jpg
        String saveName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));

        disk.setSaveName(saveName);
        disk.setUpdateTime(new Date());
        disk.setDownloadCount(0);
        disk.setType(Disk.DISK_FILE_TYPE);

        diskMapper.insert(disk);

        try {
            //保存文件到磁盘
            OutputStream outputStream = new FileOutputStream(new File(uploadPath, saveName));
            IOUtils.copy(inputStream, outputStream);

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException ex) {
            throw new ServiceException("上传文件异常",ex);
        }
    }

    /**
     * 下载文件
     * @param outputStream
     * @param disk
     */
    @Override
    @Transactional
    public void downloadFile(OutputStream outputStream, Disk disk) {
        //更新下载数量
        disk.setDownloadCount(disk.getDownloadCount() + 1);
        diskMapper.updateByPrimaryKey(disk);

        try {
            File file = new File(uploadPath, disk.getSaveName());
            if(file.exists()) {
                InputStream inputStream = new FileInputStream(file);
                IOUtils.copy(inputStream,outputStream);

                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } else {
                throw new ServiceException("下载资源不存在或已被删除");
            }
        } catch (IOException ex) {
            throw new ServiceException("下载文件异常",ex);
        }
    }
}
