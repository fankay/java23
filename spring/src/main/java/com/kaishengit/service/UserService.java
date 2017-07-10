package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import com.kaishengit.dao.WeixinDao;

/**
 * Created by fankay on 2017/7/7.
 */
public class UserService {

    private UserDao userDao;
    /*private WeixinDao weixinDao;

    public UserService(UserDao userDao,WeixinDao weixinDao) {
        this.userDao = userDao;
        this.weixinDao = weixinDao;
    }*/

    public void setMyUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
/*
    public void setWeixinDao(WeixinDao weixinDao) {
        this.weixinDao = weixinDao;
    }*/

    public void save() {
        userDao.save();
        /*if(1==1) {
            throw new RuntimeException("SQL执行出现了异常");
        }*/
    }

    public int sum() {
        return 100;
    }

}
