package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.entity.DeptExample;
import com.kaishengit.crm.mapper.DeptMapper;
import com.kaishengit.crm.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fankay on 2017/7/17.
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAllDept() {
        return deptMapper.selectByExample(new DeptExample());
    }

    @Override
    public void save(Dept dept) {
        deptMapper.insert(dept);
    }
}
