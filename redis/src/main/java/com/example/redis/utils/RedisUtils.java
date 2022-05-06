package com.example.redis.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wenxianlong
 * @date 2022/5/6 4:42 下午
 */
public class RedisUtils {

    public static String HOST = "192.168.1.216";
    public static int PORT = 6379;
    private static JedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(5);
        pool = new JedisPool(config, HOST, PORT);
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    /**
     * 使用redis的set命令实现获取分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求ID，保证同一性
     * @param timeout   过期时间，避免死锁
     * @return
     */
    public static boolean getLockBySet(String lockKey, String requestId, int timeout) {
        // 获取jedis对象，负责与远程redis服务器进行链接
        Jedis jedis = getJedis();
        String result = jedis.set(lockKey, requestId, "NX", "EX", timeout);
        if (result == "OK") {
            return true;
        }
        return false;
    }

    /**
     * 使用redis的setnx命令实现获取分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求ID，保证同一性
     * @param timeout   过期时间，避免死锁
     * @return
     */
    public static synchronized boolean getLockBySetnx(String lockKey, String requestId, int timeout) {
        // 获取jedis对象，负责与远程redis服务器进行链接
        Jedis jedis = getJedis();
        Long result = jedis.setnx(lockKey, requestId);
        if (result == 1) {
            // 设置有效期，防止死锁
            jedis.expire(lockKey, timeout);
            return true;
        }
        return false;
    }

    /**
     * 使用del命令释放锁
     *
     * @param lockKey   锁
     * @param requestId 请求ID，保证同一性
     */
    public static void releaseLockByDel(String lockKey, String requestId) {
        // 获取jedis对象，负责与远程redis服务器进行链接
        Jedis jedis = getJedis();
        // 保证同一性
        if (requestId.equals(jedis.get(lockKey))) {
            jedis.del(lockKey);
        }
    }


}
