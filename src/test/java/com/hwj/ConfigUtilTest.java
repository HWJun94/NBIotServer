package com.hwj;

import com.hwj.util.ConfigUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigUtilTest {

    @Test
    public void testGetString() {
        ConfigUtil.configure("config.properties");
        assertEquals("value", ConfigUtil.getString("testkey"));
    }

    @Test
    public void testGetStringArray() {
        String[] expected = {"value1", "value2", "value3"};
        ConfigUtil.configure("config.properties");
        String[] actual = ConfigUtil.getStringArray("testkeys");
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}
