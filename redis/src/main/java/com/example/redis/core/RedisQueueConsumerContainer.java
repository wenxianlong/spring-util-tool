package com.example.redis.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * redis对列消费者者容器
 *
 * @author hyl
 * @date 2021/10/20 9:47 上午
 */
@Slf4j
public class RedisQueueConsumerContainer {

    /**
     * 存放消费者的map  key=消费者 queueName value=消费者对象
     */
    Map<String, RedisQueueConsumer> consumerMap = new HashMap<>();

    /**
     * 容器是否初始化完毕的标示
     */
    static Boolean isRun = false;

    @Autowired
    private RedisThreadPool myRedisThreadPool;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 新增消费者
     *
     * @param consumer 消费者
     */
    public void addConsumer(RedisQueueConsumer consumer) {
        if (consumer.getQueueName() == null) {
            log.error("【添加redis队列失败】:{}", "队列名称为null");
        } else if (null == consumerMap.get(consumer.getQueueName())) {
            consumerMap.put(consumer.getQueueName(), consumer);
            log.error("【添加redis队列成功】:{}", consumer.getQueueName());
        }
    }

    /**
     * redis消费者容器初始化
     */
    public void init() {
        log.info("redis消费者容器初始化开始");
        isRun = true;
        consumerMap.forEach((k, v) -> myRedisThreadPool.executor(new RedisQueueListener(v, stringRedisTemplate)));
    }

    public void destroy() {
        log.info("redis消费者容器销毁");
        myRedisThreadPool.destroy();
    }

}
