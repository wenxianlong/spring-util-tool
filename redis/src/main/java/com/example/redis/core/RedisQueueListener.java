package com.example.redis.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 消息队列的监听器
 *
 * @author hyl
 * @date 2021/10/20 9:47 上午
 */
@Slf4j
public class RedisQueueListener implements Runnable {

    private final Long WAITTIME = 30L;
    private final RedisQueueConsumer redisQueueConsumer;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisQueueListener(RedisQueueConsumer redisQueueConsumer, StringRedisTemplate stringRedisTemplate) {
        this.redisQueueConsumer = redisQueueConsumer;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Override
    public void run() {
        log.info("redis监听器开始监听:{}", redisQueueConsumer.getQueueName());
        while (RedisQueueConsumerContainer.isRun) {
            try {
                String message = stringRedisTemplate.opsForList().rightPop(redisQueueConsumer.getQueueName(), WAITTIME, TimeUnit.SECONDS);
                if (StringUtils.isNotBlank(message)) {
                    redisQueueConsumer.getMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("redis监听器错误：{}", redisQueueConsumer.getQueueName());
            }
        }
    }
}
