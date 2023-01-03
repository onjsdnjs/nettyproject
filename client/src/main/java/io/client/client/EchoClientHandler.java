package io.client.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String sendMessage = "Hello, Netty!";

        ByteBuf messageBuffer = Unpooled.buffer();
        messageBuffer.writeBytes(sendMessage.getBytes());

        ctx.writeAndFlush(messageBuffer);
        System.out.println("send message " + sendMessage  );
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
     System.out.println("receive message " + ((ByteBuf) msg).toString(Charset.defaultCharset()) );
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught" + cause);
        ctx.close();
    }
}
