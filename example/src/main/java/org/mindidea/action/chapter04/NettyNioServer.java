/*
 * @class NettyNioServer
 * @package org.mindidea.action.chapter04
 * @date 2020/11/10 21:01
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.action.chapter04;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/10 21:01
 * @blog https://mindidea.org
 */
public class NettyNioServer {
    public static void main(String[] args) throws Exception {
        NettyNioServer server = new NettyNioServer();
        server.server(8080);
    }

    public void server(int port) throws Exception {
        final ByteBuf buf = Unpooled.copiedBuffer("\tHi!\n", Charset.forName("UTF-8"));
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ctx.writeAndFlush(buf.duplicate())
                                            .addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            ChannelFuture sync = b.bind().sync();
            sync.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
