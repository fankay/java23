package com.kaishengit.piao.systemmanager.service.impl;

import com.kaishengit.piao.systemmanager.api.UserService;
import com.kaishengit.piao.systemmanager.mapper.RoleMapper;
import com.kaishengit.piao.systemmanager.mapper.UserMapper;
import com.kaishengit.piao.systemmanager.mapper.UserRoleMapper;
import com.kaishengit.piao.systemmanager.modal.User;
import com.kaishengit.piao.systemmanager.modal.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User findByUserName(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(username);
        List<User> userList = userMapper.selectByExample(example);
        if(userList != null && !userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User findByMobile(String mobile) {
        UserExample example = new UserExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<User> userList = userMapper.selectByExample(example);
        if(userList != null && !userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }
}
