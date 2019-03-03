package com.hwj.util;

import com.hwj.config.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private static JedisPool jedisPool;

    //私有构造器
    private RedisUtil() {}

    //method
    /**
     * 连接池初始化
     */
    public static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(RedisConfig.maxTotal());
        config.setMaxIdle(RedisConfig.maxIdle());
        config.setMinIdle(RedisConfig.minIdle());
        config.setTestOnBorrow(RedisConfig.testOnBorrow());
        config.setTestOnReturn(RedisConfig.testOnReturn());

        jedisPool = new JedisPool(config, RedisConfig.ip(), RedisConfig.port());
        logger.info("JedisPool init complete");
        logger.debug("JedisPool active : {}", jedisPool.getNumActive());
    }

    /**
     * 获取连接池方法
     * @return 连接池实例
     */
    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * 从连接池中获取redis实例
     * @return redis实例
     */
    public static Jedis getJedis() {
        if (jedisPool == null)
            initPool();
        return jedisPool.getResource();
    }
}
