package com.kaishengit.piao.systemmanager.service.impl;

import com.kaishengit.piao.systemmanager.api.UserService;
import com.kaishengit.piao.systemmanager.mapper.RoleMapper;
import com.kaishengit.piao.systemmanager.mapper.UserMapper;
import com.kaishengit.piao.systemmanager.mapper.UserRoleMapper;
import com.kaishengit.piao.systemmanager.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User login(String userName, String password) {
        return null;
    }
}
