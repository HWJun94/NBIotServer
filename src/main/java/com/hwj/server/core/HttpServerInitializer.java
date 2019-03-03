package com.hwj.server.core;

import com.hwj.server.RequestDispatcher;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel>{
    //SslContext
    private static SslContext sslContext;
    private RequestDispatcher requestDispatcher;
    private static final EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(8);

    public HttpServerInitializer(SslContext sslContext, RequestDispatcher rd) {
        this.sslContext = sslContext;
        this.requestDispatcher = rd;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        if (sslContext != null)
            channelPipeline.addLast("SSL", sslContext.newHandler(socketChannel.alloc()));
        channelPipeline.addLast("decoder", new HttpRequestDecoder())
                .addLast("aggregator", new HttpObjectAggregator(1048576))
                .addLast("encoder", new HttpResponseEncoder())
                .addLast(new HttpServerExpectContinueHandler())
                .addLast(new HttpServerWorker(requestDispatcher));
    }
}
