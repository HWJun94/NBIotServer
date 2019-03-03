package com.hwj.config;

import com.hwj.util.ConfigUtil;
import com.hwj.util.RedisUtil;

public class RedisConfig {

    public static int maxTotal() {
        return ConfigUtil.getInt("redis.pool.maxTotal");
    }

    public static int maxIdle() {
        return ConfigUtil.getInt("redis.pool.maxIdle");
    }

    public static int minIdle() {
        return ConfigUtil.getInt("redis.pool.minIdle");
    }

    public static long maxWaitMillis() {
        return ConfigUtil.getLong("redis.pool.maxWaitMillis");
    }

    public static boolean testOnBorrow() {
        return ConfigUtil.getBoolean("redis.pool.testOnBorrow");
    }

    public static boolean testOnReturn() {
        return ConfigUtil.getBoolean("redis.pool.testOnReturn");
    }

    public static boolean testWhileIdle() {
        return ConfigUtil.getBoolean("redis.pool.testWhileIdle");
    }

    public static long timeBetweenEvictionRunsMillis() {
        return ConfigUtil.getLong("redis.pool.timeBetweenEvictionRunsMillis");
    }

    public static int numTestsPerEvictionRun() {
        return ConfigUtil.getInt("redis.pool.numTestsPerEvictionRun");
    }

    public static String ip() {
        return ConfigUtil.getString("redis.ip");
    }

    public static int port() {
        return ConfigUtil.getInt("redis.port");
    }
}
