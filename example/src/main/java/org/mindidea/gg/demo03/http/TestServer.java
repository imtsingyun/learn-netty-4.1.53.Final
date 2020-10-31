/*
 * @class TestServer
 * @package org.mindidea.gg.demo03.http
 * @date 2020/10/28 22:43
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo03.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/28 22:43
 * @blog https://mindidea.org
 */
public class TestServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    // 使用 NioServerSocketChannel 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    // 为 workerGroup 的 EventLoop 对应的管道设置处理器
                    .childHandler(new TestServerInitializer());

            System.out.println("服务器 is ready ......");

            ChannelFuture cf = bootstrap.bind(6688).sync();
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
