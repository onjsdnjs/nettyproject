package io.server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;

    public EchoServer() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // 핸들러 설정
                        pipeline.addLast(new EchoServerHandler());
                    }
                });
    }

    public void start(final int port) throws InterruptedException {
        System.out.println("start server " + port + " ...");
        ChannelFuture future = bootstrap.bind(port).sync();
        future.channel().closeFuture().sync();
    }

    public void close() {
        System.out.println("close server...");
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
