package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.AccountDeptExample;
import com.kaishengit.crm.entity.AccountDeptKey;
import com.kaishengit.crm.entity.AccountExample;
import com.kaishengit.crm.mapper.AccountDeptMapper;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.exception.AuthenticationException;
import com.kaishengit.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${password.salt}")
    private String passwordSalt;


    @Override
    @Transactional
    public void saveAccount(Account account, Integer[] deptIds) {
        //添加员工
        account.setPassword(DigestUtils.md5Hex(passwordSalt + account.getPassword()));
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

    /**
     * 用户登录
     * @param mobile
     * @param password
     * @throws AuthenticationException 如果账号或密码错误，则抛出该异常
     * @return
     */
    @Override
    public Account login(String mobile, String password) throws AuthenticationException {
        //根据手机号码查询Account

        Account account = accountMapper.findByMobileLoadDept(mobile);
        //根据Account的密码和password参数比较
        if(DigestUtils.md5Hex(passwordSalt + password).equals(account.getPassword())) {
            return account;
        }
        throw new AuthenticationException("账号或密码错误");
    }

    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param password 新密码
     * @param account 被修改对象
     * @throws ServiceException 如果旧密码错误，则抛出该异常
     */
    @Override
    public void changePassword(String oldPassword, String password,Account account) throws ServiceException {
        //判断旧密码是否正确
        if(DigestUtils.md5Hex(passwordSalt + oldPassword).equals(account.getPassword())) {
            account.setPassword(DigestUtils.md5Hex(passwordSalt + password));
            //修改密码
            accountMapper.updateByPrimaryKeySelective(account);
        } else {
            throw new ServiceException("旧密码错误");
        }
    }
/*
    @Override
    public List<Account> findByPage(String start, String length) {
        //PageHelper.offsetPage(Integer.valueOf(start),Integer.valueOf(length));
        List<Account> accountList = accountMapper.findAllLoadDept();
        return accountList;
    }*/
}
