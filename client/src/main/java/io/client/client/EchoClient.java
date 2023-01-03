package io.client.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
    private EventLoopGroup group;
    private Bootstrap bootstrap;

    public EchoClient() {
        group = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new EchoClientHandler());
                    }
                });
    }

    public void connect(final String host, final int port) throws InterruptedException {
        System.out.println("connect to " + host + ":" + port);
        ChannelFuture future = bootstrap.connect(host, port).sync();
        future.channel().closeFuture().sync();
    }

    public void close() {
        System.out.println("close client...");
        if (group != null) {
            group.shutdownGracefully();
        }
    }
}
