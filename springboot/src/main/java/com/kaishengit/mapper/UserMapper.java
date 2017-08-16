package com.kaishengit.mapper;

import com.kaishengit.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from t_user where id = #{id}")
    User findById(Integer id);

}
