package com.kaishengit.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RedisTest {


    @Test
    public void helloWorld() {
        Jedis jedis = new Jedis("192.168.1.110",6000);
        jedis.set("hello","redis");
        jedis.close();
    }
    @Test
    public void get() {
        Jedis jedis = new Jedis("192.168.1.110",6379);
        String result = jedis.get("hello");
        System.out.println(result);
        jedis.close();
    }

    @Test
    public void pool() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(10);
        config.setMinIdle(5);
        JedisPool pool = new JedisPool(config,"192.168.1.110",6379);

        Jedis jedis = pool.getResource();
        //jedis.lpush("user:1","aa","bb","cc");
        jedis.hset("student:1","name","jack");
        jedis.hset("student:1","address","北京");
        
        //String address = jedis.hget("student:1","address");
        //System.out.println(address);

        jedis.close();
        pool.destroy();

    }

    @Test
    public void jedisCluster() throws IOException {
        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
        hostAndPortSet.add(new HostAndPort("192.168.1.110",6000));
        hostAndPortSet.add(new HostAndPort("192.168.1.110",6001));
        hostAndPortSet.add(new HostAndPort("192.168.1.110",6002));
        hostAndPortSet.add(new HostAndPort("192.168.1.110",6003));
        hostAndPortSet.add(new HostAndPort("192.168.1.110",6004));
        hostAndPortSet.add(new HostAndPort("192.168.1.110",6005));

        JedisCluster jedisCluster = new JedisCluster(hostAndPortSet,1000,1000);
        jedisCluster.set("jedis","rose");
        jedisCluster.close();


    }


}
