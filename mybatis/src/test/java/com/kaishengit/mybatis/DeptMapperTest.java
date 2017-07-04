package com.kaishengit.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.kaishengit.entity.Dept;
import com.kaishengit.entity.User;
import com.kaishengit.mapper.DeptMapper;
import com.kaishengit.util.MyBatisUtil;

public class DeptMapperTest {

	@Test
	public void findByIdLoadUser() {
		
		SqlSession sqlSession = MyBatisUtil.getSqlSession();
		DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
		
		Dept dept = deptMapper.findByIdLoadUser(1);
		System.out.println(dept.getDeptName());
		
		List<User> userList = dept.getUserList();
		for(User user : userList) {
			System.out.println(user.getUserName());
		}
		
		
		sqlSession.close();
		
		
	}
	
}
