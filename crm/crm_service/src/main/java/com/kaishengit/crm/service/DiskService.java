package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Disk;

import java.util.List;

public interface DiskService {
    void saveNewFolder(Disk disk);

    List<Disk> findDiskByPid(Integer pId);

    Disk findById(Integer id);
}
