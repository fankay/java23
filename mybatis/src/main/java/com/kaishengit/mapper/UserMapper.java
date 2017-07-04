package com.kaishengit.mapper;

import java.util.List;

import com.kaishengit.entity.User;

public interface UserMapper {

	User findById(Integer id);
	List<User> findAll();
	void save(User user);
	void update(User user);
	void delById(Integer id);
	
	List<User> findAllLoadDept();
	
}
