package com.example.redis.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redis消费者/生产者模式配置
 *
 * @author hyl
 * @date 2021/10/20 11:11 上午
 */
@Slf4j
@Configuration
public class RedisQueueConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public RedisQueueConsumerContainer redisQueueConsumerContainer() {
        log.info("redis队列开始加载");
        RedisQueueConsumerContainer redisQueueConsumerContainer = new RedisQueueConsumerContainer();
        // 添加消费者 到消费者容器
//        redisQueueConsumerContainer.addConsumer(new BusinessLineConsumer());
//        redisQueueConsumerContainer.addConsumer(new PaymentAuditConsumer());
//        redisQueueConsumerContainer.addConsumer(new PaymentRiskControlAuditConsumer());
//        redisQueueConsumerContainer.addConsumer(new FinancingRepayApplyAuditConsumer());
//        redisQueueConsumerContainer.addConsumer(new PaymentPushOAConsumer());
//        redisQueueConsumerContainer.addConsumer(new InvoiceUploadConsumer());
//        redisQueueConsumerContainer.addConsumer(new AssetConsumer());

        log.info("redis队列开始加载成功");
        return redisQueueConsumerContainer;
    }
}
