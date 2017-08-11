package com.kaishengit.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis-cluster.xml")
public class SpringJedisTest {

    @Autowired
    private JedisCluster jedisCluster;
    //private JedisPool jedisPool;

    @Test
    public void setStringValue() throws IOException {
        //Jedis jedis = jedisPool.getResource();
        jedisCluster.set("spring","hello,Spring");
        jedisCluster.close();


        //jedis.close();
    }

   /* @Test
    public void getStringValue() {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get("spring");
        System.out.println(result);
        jedis.close();
    }*/

}
