package com.example.redis.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * redis消费者/生产者模式配置
 *
 * @author hyl
 * @date 2021/10/20 11:11 上午
 */
@Slf4j
@Component
public class RedisThreadPool {

    private ThreadPoolExecutor executor;

    private static RedisThreadPool instance;

    public RedisThreadPool() {
        if (executor == null) {
            // 用单例模式创建线程池，保留2个核心线程，最多线程为CPU个数的2n+1的两倍.
            executor = new ThreadPoolExecutor(7, 7, 0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<>(),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());

        }

    }

    public static RedisThreadPool getInstance() {
        if (instance == null) {
            instance = new RedisThreadPool();
            log.info("MyRedisThreadPool线程池已经开启");
        }
        return instance;
    }

    public void executor(Runnable runnable) {
        if (null == runnable) {
            return;
        }
        executor.execute(runnable);
    }

    public void destroy() {
        if (executor != null) {
            // 终止线程池
            executor.shutdown();
        }
    }
}
