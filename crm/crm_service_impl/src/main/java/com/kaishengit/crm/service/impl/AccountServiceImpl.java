package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Account;
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
}
