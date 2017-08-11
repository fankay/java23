package com.kaishengit.piao.systemmanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.kaishengit.piao.systemmanager.api.UserService;
import com.kaishengit.piao.systemmanager.mapper.RoleMapper;
import com.kaishengit.piao.systemmanager.mapper.UserMapper;
import com.kaishengit.piao.systemmanager.mapper.UserRoleMapper;
import com.kaishengit.piao.systemmanager.modal.Role;
import com.kaishengit.piao.systemmanager.modal.User;
import com.kaishengit.piao.systemmanager.modal.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /*@Autowired
    public void setRedisTemplate(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
    }*/

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

        //先从redis中查询是否存在对象，如果存在就取出。不存在再去数据库中查询

        String json = redisTemplate.opsForValue().get("user:"+mobile);
        User user = null;

        if(json == null) {
            UserExample example = new UserExample();
            example.createCriteria().andMobileEqualTo(mobile);
            List<User> userList = userMapper.selectByExample(example);
            if(userList != null && !userList.isEmpty()) {
                user =  userList.get(0);
                //放入redis
                redisTemplate.opsForValue().set("user:"+mobile,JSON.toJSONString(user));
                return user;
            }
            return null;
        }  else {
            return JSON.parseObject(json,User.class);
        }
    }

    @Override
    public List<String> findRoleNamesByUserId(String id) {
        String json = redisTemplate.opsForValue().get("user:"+id+":roleNames");
        if(json == null) {
            List<Role> roleList = roleMapper.findByUserId(id);
            List<String> roleNames = new ArrayList<>();
            for (Role role : roleList) {
                roleNames.add(role.getRoleName());
            }
            //写入redis
            redisTemplate.opsForValue().set("user:"+id+":roleNames", JSON.toJSONString(roleNames));
            return roleNames;
        } else {
            return JSON.parseObject(json,ArrayList.class);
        }
    }
}
