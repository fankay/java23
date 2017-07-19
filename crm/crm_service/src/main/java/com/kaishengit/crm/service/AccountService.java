package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.exception.AuthenticationException;
import com.kaishengit.exception.ServiceException;

import java.util.List;

/**
 * Created by fankay on 2017/7/17.
 */
public interface AccountService {

    void saveAccount(Account account,Integer[] deptId);

    List<Account> findAllAccount();

    Long countAll();

    Long countByDeptId(Integer deptId);

    List<Account> findByDeptId(Integer deptId);

    void delAccountById(Integer id);

    Account login(String mobile, String password)  throws AuthenticationException;

    void changePassword(String oldPassword, String password,Account account) throws ServiceException;
}
