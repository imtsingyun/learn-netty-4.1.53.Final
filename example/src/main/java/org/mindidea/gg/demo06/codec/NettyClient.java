/*
 * @class NettyClient
 * @package org.mindidea.gg.demo02
 * @date 2020/10/28 20:23
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo06.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/28 20:23
 * @blog https://mindidea.org
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        try {
            // 设置参数     设置线程组
            bootstrap.group(eventExecutors)
                    // 设置客户端通道的实现类（反射）
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("encoder", new ProtobufEncoder())
                                    // 加入自己的处理器
                                    .addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("client is ok ...");

            ChannelFuture cf = bootstrap.connect("127.0.0.1", 6688).sync();
            cf.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
