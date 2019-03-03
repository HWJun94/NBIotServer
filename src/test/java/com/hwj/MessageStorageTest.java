package com.hwj;

import com.hwj.db.DeviceStorage;
import com.hwj.util.ConfigUtil;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class MessageStorageTest {

    @Test
    public void testSet() {
        ConfigUtil.configure("config.properties");
        boolean result = DeviceStorage.set("666666", "aaaaaa");
        Assert.assertTrue(result);
    }

    @Test
    public void testGetDeviceIdAndGetIMEI() {
        ConfigUtil.configure("config.properties");
        Assert.assertEquals("abcde", DeviceStorage.getDeviceId("12345"));
        Assert.assertEquals("12345", DeviceStorage.getIMEI("abcde"));
    }

    @Test
    public void testDelete() {
        ConfigUtil.configure("config.properties");
        Assert.assertTrue(DeviceStorage.delete("12345"));
    }
}
