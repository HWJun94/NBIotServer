package com.hwj;

import com.hwj.config.RedisConfig;
import com.hwj.util.ConfigUtil;
import com.hwj.util.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisUtilTest {

    @Test
    public void testInitPool() {
        ConfigUtil.configure("config.properties");
        RedisUtil.initPool();
        Assert.assertNotNull(RedisUtil.getJedisPool());
    }

    @Test
    public void getJedis() {
        testInitPool();
        Jedis jedis = RedisUtil.getJedis();
        Assert.assertNotNull(jedis);
        System.out.println("ActiveNum:" + RedisUtil.getJedisPool().getNumActive());
        for (int i = 0; i < 20; i++) {
            Jedis tmp = RedisUtil.getJedis();
            tmp.close();
            System.out.println("ActiveNum:" + RedisUtil.getJedisPool().getNumActive());
        }
        jedis.close();
        System.out.println("ActiveNum:" + RedisUtil.getJedisPool().getNumActive());
        System.out.println("IdleNum:" + RedisUtil.getJedisPool().getNumIdle());
    }
}
