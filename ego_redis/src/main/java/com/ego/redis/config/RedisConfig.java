package com.ego.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Auther: liuxw
 * @Date: 2019/8/12
 * @Description: com.ego.redis.config
 * @version: 1.0
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> getTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> redis =new RedisTemplate<String,Object>();
        redis.setKeySerializer(new StringRedisSerializer());
        redis.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redis.setConnectionFactory(factory);
        return redis;
    }


    }
