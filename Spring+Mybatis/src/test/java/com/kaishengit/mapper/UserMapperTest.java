package com.kaishengit.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kaishengit.Application;
import com.kaishengit.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void save() {
		User user = new User("JackJack","NewYork","123123",1);
		userMapper.save(user);
	}
	
}
