package com.zs.admin;

import com.zs.common.redis.RedisUtil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class zsAdminApplicationTests {


    @Autowired
    private RedisProperties redisProperties;

    @Test
    void contextLoads() {

        RedisClient redisClient = RedisClient.create("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        System.out.println("Redis connection established successfully");
        connection.close();
        redisClient.shutdown();
    }

    @Resource
    public RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Test
    public void test1 () {
        System.out.println("8888*****" + redisTemplate.opsForValue().get("zs"));
        System.out.println(redisUtil.get("zs"));
    }

}
