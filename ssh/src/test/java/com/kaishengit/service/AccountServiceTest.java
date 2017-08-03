package com.kaishengit.service;

import com.kaishengit.pojo.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void save() throws Exception {
        Account account = new Account();
        account.setUserName("Rose");
        account.setPassword("123123");
        account.setCreateTime(new Date());

        accountService.save(account);
    }

    @Test
    public void findById() throws Exception {
        Account account = accountService.findById(1020);
        System.out.println(account.getUserName());
    }

}