package com.kaishengit.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Options.FlushCachePolicy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kaishengit.entity.User;

@CacheNamespace
public interface UserMapper {

	@Select("select * from t_user where id = #{id}")
	@Options(useCache=false)
	User findById(Integer id);
	
	@Select("select * from t_user")
	List<User> findAll();
	
	@Insert("insert into t_user(user_name,password,address) values(#{userName},#{password},#{address})")
	@Options(useGeneratedKeys=true,keyProperty="id",flushCache=FlushCachePolicy.FALSE)
	void save(User user);
	
	void batchSave(List<User> userList);
	
	@Update("update t_user set user_name=#{userName},password=#{password},address=#{address} where id = #{id}")
	void update(User user);
	@Delete("delete from t_user where id = #{id}")
	void delById(Integer id);
	
	void delByIds(@Param("list")List<Integer> idList);
	
	List<User> findAllLoadDept();
	
	@Select("select * from t_user where user_name = #{param1} and password = #{param2}")
	User findByUserNameAndPassword(String userName,
								   String password);
	
	@Select("select * from t_user where user_name = #{name} and password = #{pwd}")
	User findByMapParam(Map<String,Object> params);
	
	List<User> searchByNameAndAddress(Map<String,Object> params);
	
}
