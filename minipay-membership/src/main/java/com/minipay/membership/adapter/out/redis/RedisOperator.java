package com.minipay.membership.adapter.out.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisOperator {

    private final RedisTemplate<String, Object> redisTemplate;

    public void put(String key, Object value, long expirationTime) {
        redisTemplate.opsForValue().set(
                key,
                value.toString(),
                expirationTime,
                TimeUnit.MILLISECONDS);
    }

    public Optional<Object> get(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
