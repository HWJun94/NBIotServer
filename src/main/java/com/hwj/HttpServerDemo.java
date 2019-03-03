package com.hwj;

import com.hwj.handler.DeviceAddedHandler;
import com.hwj.handler.DeviceDataChangedHandler;
import com.hwj.handler.HttpHandlerDemo;
import com.hwj.mqtt.BEMqttClient;
import com.hwj.server.HttpRequestDispatcher;
import com.hwj.server.Router;
import com.hwj.server.core.HttpServer;
import com.hwj.tinymq.ConsumerTask;
import com.hwj.tinymq.DeviceDataConsumer;
import com.hwj.tinymq.TinyMessageQueue;
import com.hwj.util.ConfigUtil;

public class HttpServerDemo {

    public static void main(String[] args) throws Exception {
        //初始化配置文件
        ConfigUtil.configure("config.properties");

        //初始化消息队列
        TinyMessageQueue messageQueue = new TinyMessageQueue(1,1);

        //初始化mqtt
        BEMqttClient mqttClient = new BEMqttClient();
        mqttClient.init();
        mqttClient.connect();

        //初始化队列消费者
        for (int i = 0; i < TinyMessageQueue.nConsumers(); i++) {
            ConsumerTask task = new DeviceDataConsumer(mqttClient);
            TinyMessageQueue.submit(task);
        }

        //初始化server
        Router router = new Router();
        setHanlers(router);
        HttpRequestDispatcher rd = new HttpRequestDispatcher(router);
        HttpServer server = new HttpServer(rd);
        server.start();
    }

    public static void setHanlers(Router router) {
        router.handler("/NBIoT/notify/deviceDataChanged", new DeviceDataChangedHandler())
                .handler("/NBIoT/notify/deviceAdded", new DeviceAddedHandler());
    }
}
