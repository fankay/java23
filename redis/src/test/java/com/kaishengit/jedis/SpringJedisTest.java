package com.kaishengit.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class SpringJedisTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void setStringValue() {
        Jedis jedis = jedisPool.getResource();
        jedis.set("spring","hello,Spring");
        jedis.close();
    }

    @Test
    public void getStringValue() {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get("spring");
        System.out.println(result);
        jedis.close();
    }

}
