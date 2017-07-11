package com.kaishengit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.User;
import com.kaishengit.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	

	public void save(User user) {
		userMapper.save(user);
	}
	
	public User findById(Integer id) {
		return userMapper.findById(id);
	}
	
	public PageInfo<User> findByPage(int pageNo,int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<User> userList = userMapper.findAll();
		return new PageInfo<User>(userList);
	}
	
}
