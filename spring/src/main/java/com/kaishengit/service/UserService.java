package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import com.kaishengit.dao.WeixinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fankay on 2017/7/7.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private WeixinDao weixinDao;

    /*@Autowired
    public UserService(UserDao userDao,WeixinDao weixinDao) {
        this.userDao = userDao;
        this.weixinDao = weixinDao;
    }*/

    public void save() {
        userDao.save();
        weixinDao.sendWeiXin();
    }
}
