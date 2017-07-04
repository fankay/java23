package com.kaishengit.mapper;

import com.kaishengit.entity.Dept;

public interface DeptMapper {

	Dept findByIdLoadUser(Integer id);
	
}
