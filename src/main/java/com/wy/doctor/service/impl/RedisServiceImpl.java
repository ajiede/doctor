package com.wy.doctor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl {
    private final StringRedisTemplate stringRedisTemplate;
    private static final String PREFIX = "verification_code:";

    public void saveVerificationCode(String phoneNumber, String code) {
        String key = PREFIX + phoneNumber;
        stringRedisTemplate.opsForValue().set(key, code, Duration.ofMinutes(5)); // 设置过期时间为5分钟
    }

    public String getVerificationCode(String phoneNumber) {
        String key = PREFIX + phoneNumber;
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void deleteVerificationCode(String phoneNumber) {
        String key = PREFIX + phoneNumber;
        stringRedisTemplate.delete(key);
    }
}
