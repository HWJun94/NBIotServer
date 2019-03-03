package com.hwj.handler;

import com.hwj.server.RequestHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHandlerDemo implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(HttpHandlerDemo.class);

    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest request) {
        logger.debug("Receive Request: {}", request.toString() + "\n" + request.content().toString(CharsetUtil.UTF_8));
        sendOKResponse(ctx, request);

    }

    private void sendOKResponse(ChannelHandlerContext ctx, FullHttpRequest request) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        ChannelFuture future = ctx.writeAndFlush(response);
        future.addListener(ChannelFutureListener.CLOSE);
    }
}
