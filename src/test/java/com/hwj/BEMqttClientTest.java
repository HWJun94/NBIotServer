package com.hwj;

import com.hwj.mqtt.BEMqttClient;
import com.hwj.util.ConfigUtil;
import org.junit.Assert;
import org.junit.Test;

public class BEMqttClientTest {

    @Test
    public void testConnect() {
        ConfigUtil.configure("config.properties");
        BEMqttClient client = new BEMqttClient();
        client.connect();
        Assert.assertTrue(client.isConnected());
    }

    @Test
    public void testPublish() {
        ConfigUtil.configure("config.properties");
        BEMqttClient client = new BEMqttClient();
        client.connect();
        if (client.isConnected()) {
            try {
                client.publish("bupt/esp32/1234", 0, "hello world from BEMqttClient");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
