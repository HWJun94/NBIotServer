package com.hwj.db;

import com.hwj.util.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class DeviceStorage {
    private static final String IMEI_KEY = "IMEI";
    private static final String DEVICEID_KEY = "deviceId";


    public static String getDeviceId(String IMEI) {
        Jedis jedis = RedisUtil.getJedis();
        String deviceId;
        try {
            deviceId = jedis.hget(IMEI_KEY, IMEI);
        } finally {
            jedis.close();
        }
        return deviceId;
    }

    public static String getIMEI(String deviceId) {
        Jedis jedis = RedisUtil.getJedis();
        String IMEI;
        try {
            IMEI = jedis.hget(DEVICEID_KEY, deviceId);
        } finally {
            jedis.close();
        }
        return IMEI;
    }

    public static boolean set(String IMEI, String deviceId) {
        Jedis jedis = RedisUtil.getJedis();
        try {
            jedis.hset(IMEI_KEY, IMEI, deviceId);
            jedis.hset(DEVICEID_KEY, deviceId, IMEI);
        } finally {
            jedis.close();
        }
        return true;
    }

    public static boolean delete(String IMEI) {
        String deviceId = getDeviceId(IMEI);
        Jedis jedis = RedisUtil.getJedis();
        try {
            jedis.hdel(IMEI_KEY, IMEI);
            jedis.hdel(DEVICEID_KEY, deviceId);
        } finally {
            jedis.close();
        }
        return true;
    }
}
