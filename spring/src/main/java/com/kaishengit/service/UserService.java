package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * Created by fankay on 2017/7/11.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void save(User user) throws SQLException {
        userDao.save(user);
        userDao.save(user);
    }

}
