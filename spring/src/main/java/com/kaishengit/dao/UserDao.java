package com.kaishengit.dao;

import com.kaishengit.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(User user) {
        String sql = "insert into t_user(user_name,password,address,dept_id) values(?,?,?,?)";
        jdbcTemplate.update(sql,user.getUser_name(),user.getPassword(),user.getAddress(),user.getDept_id());
    }

    public User findById(Integer id) {
        String sql = "select * from t_user where id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(),id);
    }

    public User findByUserName(String userName) {
        String sql = "select * from t_user where user_name = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),userName);
    }

    public List<User> findAll() {
        String sql = "select * from t_user";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
    }

    public List<User> findByAddress(String address) {
        String sql = "select * from t_user where address = ?";
        return jdbcTemplate.query(sql, new UserRowMapper(),address);
    }

    public Long count() {
        String sql = "select count(*) from t_user";
        return jdbcTemplate.queryForObject(sql,Long.class);
    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUser_name(resultSet.getString("user_name"));
            user.setDept_id(resultSet.getInt("dept_id"));
            user.setAddress(resultSet.getString("address"));
            user.setPassword(resultSet.getString("password"));
            user.setId(resultSet.getInt("id"));
            return user;
        }
    }

}
