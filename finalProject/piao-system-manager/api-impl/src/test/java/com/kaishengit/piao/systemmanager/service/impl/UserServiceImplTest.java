package com.kaishengit.piao.systemmanager.service.impl;

import com.kaishengit.piao.systemmanager.api.UserService;
import com.kaishengit.piao.systemmanager.modal.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring*.xml")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void findByMobile() throws Exception {

        User user1 = userService.findByMobile("189");
        User user2 = userService.findByMobile("189");
        System.out.println(user2.getName());
    }

    @Test
    public void findRoleNamesByUserId() {
        List<String> roleNames = userService.findRoleNamesByUserId("1");
        for(String name : roleNames) {
            System.out.println(name);
        }
    }

}