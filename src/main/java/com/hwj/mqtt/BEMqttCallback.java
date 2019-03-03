package com.hwj.mqtt;

import com.hwj.tinymq.TinyMessageQueue;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BEMqttCallback implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(BEMqttCallback.class);
    private BEMqttClient mqttClient;

    public BEMqttCallback(BEMqttClient client) {
        this.mqttClient = client;
    }

    /**
     * 断线重连方法
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        logger.error("Mqtt connection lost:{}", throwable);
        logger.warn("Mqtt reconnecting...");
        mqttClient.connect();
    }

    /**
     * 订阅主题消息的回调方法
     * @param s 消息的topic
     * @param mqttMessage 接收到的消息
     * @throws Exception
     */
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        logger.info("Mqtt client received");
        logger.debug("Mqtt received: [{}, {}, {}]", s, mqttMessage.getQos(), new String(mqttMessage.getPayload()));
        String message = new String(mqttMessage.getPayload());
    }

    /**
     * publish执行完毕的回调方法
     * @param iMqttDeliveryToken 执行结果
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        logger.info("publish complete");
    }
}
