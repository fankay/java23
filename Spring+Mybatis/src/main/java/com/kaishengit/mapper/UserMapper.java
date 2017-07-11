package com.kaishengit.mapper;

import java.util.List;

import com.kaishengit.entity.User;

public interface UserMapper {

	void save(User user);
	
	User findById(Integer id);
	
	List<User> findAll();
}
