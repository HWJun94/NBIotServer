package com.hwj.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.net.URL;

public class ConfigUtil {
    private static PropertiesConfiguration configuration;

    public static void configure(String fileName) {
        URL url = ConfigUtil.class.getClassLoader().getResource(fileName);
        try {
            configuration = new PropertiesConfiguration(url);
            configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        return configuration.getString(key);
    }

    public static int getInt(String key) {
        return configuration.getInt(key);
    }

    public static long getLong(String key) {
        return configuration.getLong(key);
    }

    public static boolean getBoolean(String key) {
        return configuration.getBoolean(key);
    }

    public static String[] getStringArray(String key) {
        return configuration.getStringArray(key);
    }
}
