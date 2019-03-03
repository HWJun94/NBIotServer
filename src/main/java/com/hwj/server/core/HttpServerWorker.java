package com.hwj.server.core;

import com.hwj.server.RequestDispatcher;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServerWorker extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpServerWorker.class);
    private RequestDispatcher requestDispatcher;

    //constructor
    public HttpServerWorker(RequestDispatcher rd) {
        this.requestDispatcher = rd;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //handle the request
        FullHttpRequest request = (FullHttpRequest)msg;
        if (requestDispatcher == null) {
            ctx.close();
            throw new Exception("There should be a RequestDispatcher and not null!");
        }
        requestDispatcher.dispatch(ctx, request);
        super.channelRead(ctx, request);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("Channel Active");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("Channel Inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        logger.info("Exception: ", cause);
    }
}
