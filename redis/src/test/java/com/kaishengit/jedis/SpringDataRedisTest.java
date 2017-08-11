package com.kaishengit.jedis;

import com.kaishengit.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-data-redis.xml")
public class SpringDataRedisTest {


    RedisTemplate<String,User> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
    }

    @Test
    public void saveUserToRedis() {
        User user = new User();
        user.setId(1001);
        user.setUserName("赵晓丽");
        user.setAddress("深圳");

        redisTemplate.opsForValue().set("user:1001",user);
    }

    @Test
    public void getUserFromRedis() {
        User user = redisTemplate.opsForValue().get("user:1001");
        System.out.println(user.getUserName());
        System.out.println(user.getId());
        System.out.println(user.getAddress());
    }


   /* @Test
    public void setStringValue() {
        redisTemplate.opsForValue().set("SpringData","redis");
    }
    @Test
    public void getStringValue() {
        String reuslt = redisTemplate.opsForValue().get("SpringData");
        System.out.println(reuslt);
    }*/

}
