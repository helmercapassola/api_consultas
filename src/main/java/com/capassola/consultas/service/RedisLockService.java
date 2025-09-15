package com.capassola.consultas.service;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class RedisLockService {
    private final StringRedisTemplate redisTemplate;


    public RedisLockService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public  boolean tryLock(String key, long timeoutMs){
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, "locked", Duration.ofMillis(timeoutMs));
        if(Boolean.TRUE.equals(success)){
            redisTemplate.expire(key, timeoutMs, TimeUnit.MILLISECONDS);
            return  true;
        }
        return  false;
    }

    public void  releaseLock(String key){
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }
    }
}
