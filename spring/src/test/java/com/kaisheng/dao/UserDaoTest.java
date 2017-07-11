package com.kaisheng.dao;

import com.kaishengit.Application;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by fankay on 2017/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void save() {
        User user = new User();
        user.setUser_name("Spring_JDBC");
        user.setPassword("123123");
        user.setAddress("北京");
        user.setDept_id(1);

        userDao.save(user);
    }

    @Test
    public void findById() {
        User user = userDao.findById(1);
        Assert.assertNotNull(user);
    }

    @Test
    public void findByAddress() {
        List<User> users = userDao.findByAddress("北京");
        for(User user : users) {
            System.out.println(user.getUser_name() + "->" + user.getAddress());
        }
    }
    @Test
    public void count() {
        Long count = userDao.count();
        Assert.assertEquals(13L,count.longValue());
    }

    @Test
    public void findByUserName() {
        User user = userDao.findByUserName("Spring_JDBC");
        Assert.assertEquals(user.getId().intValue(),22);
    }

    @Test
    public void findAll() {
        List<User> userList = userDao.findAll();
        Assert.assertEquals(userList.size(),13);
    }

}
