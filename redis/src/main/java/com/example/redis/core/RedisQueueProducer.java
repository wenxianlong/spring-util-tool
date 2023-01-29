package com.example.redis.core;

import com.example.redis.enums.MessageTopic;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 生产者
 *
 * @author hyl
 * @date 2021/10/20 9:47 上午
 */
@Component
public class RedisQueueProducer {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisQueueProducer(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 发送消息
     *
     * @param topic   消息主题
     * @param message 消息
     */
    public void sendMessage(MessageTopic topic, String message) {
        stringRedisTemplate.opsForList().leftPush(topic.getTopic(), message);
    }
}
