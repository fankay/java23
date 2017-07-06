package com.kaishengit.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kaishengit.entity.User;

public interface UserMapper {

	User findById(Integer id);
	List<User> findAll();
	void save(User user);
	void batchSave(List<User> userList);
	void update(User user);
	void delById(Integer id);
	void delByIds(@Param("list")List<Integer> idList);
	
	List<User> findAllLoadDept();
	User findByUserNameAndPassword(String userName,
								   String password);
	
	User findByMapParam(Map<String,Object> params);
	
	List<User> searchByNameAndAddress(Map<String,Object> params);
	
}
