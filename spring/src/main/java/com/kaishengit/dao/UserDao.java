package com.kaishengit.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.inject.Named;

/**
 * Created by fankay on 2017/7/7.
 */
//@Repository("dao")
//@Repository
@Named
//@Lazy
//@Scope("prototype")
public class UserDao {

    /*public UserDao () {
        System.out.println("create userDao");
    }

    public void init() {
        System.out.println("init method");
    }

    public void destroy() {
        System.out.println("destroy method");
    }*/

    public void save() {
        System.out.println("userDao saved.....");
    }
}
