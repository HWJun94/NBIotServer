package com.hwj.tinymq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceDataProductor implements ProductorTask {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDataProductor.class);
    private String content;

    //constructor
    public DeviceDataProductor(String content) {
        this.content = content;
    }
    @Override
    public void run() {
        if (content == null || content.equals(""))
            return;
        //消息入队
        try {
            TinyMessageQueue.put(content);
        } catch (Exception e) {
            logger.error("{}", e);
        }
        logger.info("Product data, TinyMessageQueue size:{}", TinyMessageQueue.size());
    }
}
