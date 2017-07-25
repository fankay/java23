package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.entity.DiskExample;
import com.kaishengit.crm.mapper.DiskMapper;
import com.kaishengit.crm.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiskServiceImpl implements DiskService {

    @Autowired
    private DiskMapper diskMapper;

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
}
