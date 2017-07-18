package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.AccountDeptExample;
import com.kaishengit.crm.entity.AccountDeptKey;
import com.kaishengit.crm.entity.AccountExample;
import com.kaishengit.crm.mapper.AccountDeptMapper;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by fankay on 2017/7/17.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountDeptMapper accountDeptMapper;


    @Override
    @Transactional
    public void saveAccount(Account account, Integer[] deptIds) {
        //添加员工
        account.setCreateTime(new Date());
        accountMapper.insert(account);
        //添加员工和部门关系
        for(Integer deptId : deptIds) {
            AccountDeptKey accountDeptKey = new AccountDeptKey();
            accountDeptKey.setDeptId(deptId);
            accountDeptKey.setAccountId(account.getId());

            accountDeptMapper.insert(accountDeptKey);
        }
    }

    @Override
    public List<Account> findAllAccount() {
        return accountMapper.selectByExample(new AccountExample());
    }

    @Override
    public Long countAll() {
        return accountMapper.countByExample(new AccountExample());
    }

    @Override
    public Long countByDeptId(Integer deptId) {
        if(new Integer(1000).equals(deptId)) {
            deptId = null;
        }
        return accountMapper.countByDeptId(deptId);
    }

    @Override
    public List<Account> findByDeptId(Integer deptId) {
        if(new Integer(1000).equals(deptId)) {
            deptId = null;
        }
        return accountMapper.findByDeptId(deptId);
    }

    @Override
    @Transactional
    public void delAccountById(Integer id) {
        //删除关系
        AccountDeptExample accountDeptExample = new AccountDeptExample();
        accountDeptExample.createCriteria().andAccountIdEqualTo(id);
        accountDeptMapper.deleteByExample(accountDeptExample);
        //删除员工
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andIdEqualTo(id);
        accountMapper.deleteByExample(accountExample);
    }
/*
    @Override
    public List<Account> findByPage(String start, String length) {
        //PageHelper.offsetPage(Integer.valueOf(start),Integer.valueOf(length));
        List<Account> accountList = accountMapper.findAllLoadDept();
        return accountList;
    }*/
}
