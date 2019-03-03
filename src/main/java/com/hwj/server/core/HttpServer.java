package com.hwj.server.core;

import com.hwj.server.RequestDispatcher;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

public class HttpServer {
    private static boolean ssl;
    private static SslContext sslContext;
    private static String ip;
    private static int serverPort;
    private RequestDispatcher requestDispatcher;

    //constructor
    public HttpServer(RequestDispatcher rd) {
        this("192.168.6.125", 8080, rd);
    }

    public HttpServer(String ip, int serverPort, RequestDispatcher rd) {
        this(ip, serverPort, false, null, rd);
    }

    public HttpServer(String ip, int serverPort, boolean ssl, SslContext sslContext, RequestDispatcher rd) {
        this.ip = ip;
        this.serverPort = serverPort;
        this.ssl = ssl;
        this.sslContext = sslContext;
        this.requestDispatcher = rd;
    }

    /**
     * HttpSever 的启动方法，包含初始化过程以及优雅退出
     * @throws Exception
     */
    public void start() throws Exception {
        //Configure the server
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.option(ChannelOption.SO_BACKLOG, 1024);
            boot.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpServerInitializer(sslContext, requestDispatcher));
            Channel channel = boot.bind(ip, serverPort).sync().channel();
            channel.closeFuture().sync();
        } finally {
            //Shutdown Gracefully
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
