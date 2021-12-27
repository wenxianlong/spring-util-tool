package com.wxl.token.boot.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class JWTTokenConfiguration {

    @Value("${token.expire.seconds}")
    private int tokenExpireSeconds;
    @Value("${single.token.with.user}")
    private boolean singleTokenWithUser;
    @Value("${flush.expire.after.operation}")
    private boolean flushExpireAfterOperation;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public TokenManager tokenManager(){
        final RedisTokenManager redisTokenManager=new RedisTokenManager();
        redisTokenManager.setStringRedisTemplate(stringRedisTemplate);
        redisTokenManager.setTokenExpireSeconds(tokenExpireSeconds);
        redisTokenManager.setSingleTokenWithUser(singleTokenWithUser);
        redisTokenManager.setFlushExpireAfterOperation(flushExpireAfterOperation);
        return redisTokenManager;
    }
}
