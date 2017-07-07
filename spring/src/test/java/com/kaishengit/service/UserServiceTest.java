package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fankay on 2017/7/7.
 */
public class UserServiceTest {

    @Test
    public void save() {


        /*UserDao userDao = new UserDao();

        UserService userService = new UserService();
        userService.setUserDao(userDao);
        userService.save();
*/

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        /*DiDemo diDemo = (DiDemo) ctx.getBean("diDemo");
        diDemo.show();*/

        UserService userService = (UserService) ctx.getBean("userService");
        userService.save();


    }
}
