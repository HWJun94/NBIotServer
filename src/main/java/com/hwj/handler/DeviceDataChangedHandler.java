package com.hwj.handler;

import com.hwj.server.RequestHandler;
import com.hwj.tinymq.DeviceDataProductor;
import com.hwj.tinymq.ProductorTask;
import com.hwj.tinymq.TinyMessageQueue;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceDataChangedHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDataChangedHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest request) {
        logger.info("Received request, Type: DeviceDataChanged");
        String body = request.content().toString(CharsetUtil.UTF_8);
        if (!body.equals("")) {
            logger.info("{}\n{}", request.toString(), body);
            ProductorTask task = new DeviceDataProductor(body);
            try {
                TinyMessageQueue.submit(task);
            } catch (Exception e) {
                logger.error("{}", e);
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
