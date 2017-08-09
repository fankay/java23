package com.kaishengit.piao.systemmanager.mapper;

import com.kaishengit.piao.systemmanager.modal.Role;
import com.kaishengit.piao.systemmanager.modal.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    List<Role> findByUserId(String userId);
}