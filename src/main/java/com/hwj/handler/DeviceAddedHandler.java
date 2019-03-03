package com.hwj.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hwj.db.DeviceStorage;
import com.hwj.server.RequestHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceAddedHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeviceAddedHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest request) {
        logger.info("Received request, Type: DeviceAdded");
        String body = request.content().toString(CharsetUtil.UTF_8);
        if (!body.equals("")) {
            JSONObject jsonObject = JSON.parseObject(body);
            String deviceId = jsonObject.getString("deviceId");
            String IMEI = jsonObject.getJSONObject("deviceInfo").getString("nodeId");
            if (deviceId != null && IMEI != null) {
                DeviceStorage.set(IMEI, deviceId);
                logger.info("Registerd a new device: [IMEI: {}, deviceId: {}]", IMEI, deviceId);
            }
        }
        sendOKResponse(ctx, request);
    }

    private void sendOKResponse(ChannelHandlerContext ctx, FullHttpRequest request) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        ChannelFuture future = ctx.writeAndFlush(response);
        future.addListener(ChannelFutureListener.CLOSE);
    }
}
