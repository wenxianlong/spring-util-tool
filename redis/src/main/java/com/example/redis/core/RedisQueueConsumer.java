package com.example.redis.core;

/**
 * redis队列消费者接口
 *
 * @author hyl
 * @date 2021/10/20 9:47 上午
 */
public interface RedisQueueConsumer {

    /**
     * 获取队列名称
     *
     * @return 主题
     */
    String getQueueName();

    /**
     * 获取监听器返回的消息
     *
     * @param message 消息
     */
    void getMessage(String message) throws InterruptedException;

    /**
     * 获取监听器返回的错误消息
     *
     * @param error 异常信息
     */
    void error(String error);
}
