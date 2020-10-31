/*
 * @class NettyServer
 * @package org.mindidea.gg.demo02
 * @date 2020/10/27 23:52
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务器
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/27 23:52
 * @blog https://mindidea.org
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        // bossGroup 只处理连接请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(2);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(4);

        try {
            // 创建报务器端的启动对象, 配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    // 使用 NioServerSocketChannel 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    // 设置线程队列得到连接的个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 为 workerGroup 的 EventLoop 对应的管道设置处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 通道初始化    给 pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("服务器 is ready ......");

            ChannelFuture cf = bootstrap.bind(6688).sync();

            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听端口 6688 成功");
                    } else {
                        System.out.println("监听端口 6688 失败");
                    }
                }
            });

            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
