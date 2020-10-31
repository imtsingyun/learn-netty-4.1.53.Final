/*
 * @class NettyServer
 * @package org.mindidea.netty.demo01
 * @date 2020/10/26 22:27
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.demo02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/26 22:27
 * @Blog https://mindidea.org
 */
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bind(18080);
    }

    private void bind(int port) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childrenGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(parentGroup, childrenGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childHandler(new MyChannelInitializer());

        try {
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            childrenGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }
    }
}
