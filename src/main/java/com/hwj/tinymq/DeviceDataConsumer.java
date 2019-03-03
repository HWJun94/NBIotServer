package com.hwj.tinymq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hwj.db.DeviceStorage;
import com.hwj.mqtt.BEMqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceDataConsumer implements ConsumerTask {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDataConsumer.class);
    private BEMqttClient mqttClient;

    public DeviceDataConsumer(BEMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void run() {
        while(true) {
            try {
                //取出队列消息
                String content = TinyMessageQueue.take();
                //json解析
                JSONObject jsonObject = JSON.parseObject(content);
                String deviceId = jsonObject.getString("deviceId");
                String IMEI = DeviceStorage.getIMEI(deviceId);
                if (IMEI == null) {
                    logger.warn("There isnt a device in DB about this data:{}", content);
                    return;
                }
                String topic = "/bupt/admin/" + IMEI;
                String service = jsonObject.getString("service");
                logger.debug("JSON parse : \n{}", service);
                mqttClient.publish(topic, 0, service);
                logger.info("Consume data, TinyMessageQueue size:{}", TinyMessageQueue.size());
            } catch (Exception e) {
                logger.error("{}", e);
            }
        }
    }
}
