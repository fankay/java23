package com.kaisheng.dao;

import com.kaishengit.dao.UserDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by fankay on 2017/7/7.
 */
public class UserDaoTest {

    @Test
    public void save() {
        //1. 创建Spring容器
        // 从classpath中读取spring的配置文件
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2. 从容器中获取对象
        UserDao userDao = (UserDao) ctx.getBean("com.kaishengit.dao.UserDao#1");
        userDao.save();

        ctx.close();


        /*UserDao userDao2 = (UserDao) ctx.getBean("userDao");

        System.out.println(userDao == userDao2);*/
    }

}
