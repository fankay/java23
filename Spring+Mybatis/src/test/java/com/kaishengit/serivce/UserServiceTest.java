package com.kaishengit.serivce;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.kaishengit.Application;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void save() {
		
		User user = new User("TomTom","zz","123123",1);
		userService.save(user);
	}
	
	@Test
	public void findById() {
		User user = userService.findById(1);
		System.out.println(user.getUserName());
	}
	
	@Test
	public void findByPage() {
		PageInfo<User> pageInfo = userService.findByPage(1, 5);
		List<User> userList = pageInfo.getList();
		for(User user : userList) {
			System.out.println(user);
		}
	}

}
