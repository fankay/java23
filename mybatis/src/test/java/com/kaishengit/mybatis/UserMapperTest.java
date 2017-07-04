package com.kaishengit.mybatis;


import com.kaishengit.entity.User;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.util.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMapperTest {

	private Logger logger = LoggerFactory.getLogger(UserMapperTest.class);
	
	@Test
	public void findById() {
		SqlSession sqlSession = MyBatisUtil.getSqlSession();
		
		//!!! MyBatis根据定义的Mapper接口动态的生成该接口的实现类
		//接口指向实现类
		//动态代理模式
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = userMapper.findById(1);
		
		logger.debug("user: {}",user);
		
		sqlSession.close();
	}
	
	
	@Test
	public void save() {
		
		SqlSession sqlSession = MyBatisUtil.getSqlSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		User user = new User();
		user.setAddress("伦敦");
		user.setPassword("777888");
		user.setUserName("James");
		
		userMapper.save(user);
		
		
		sqlSession.commit();
		sqlSession.close();
		
		
	}
	
	
	
	
	
	
	
	
	
}
