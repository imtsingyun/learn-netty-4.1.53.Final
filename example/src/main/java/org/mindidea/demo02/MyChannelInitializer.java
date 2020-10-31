/*
 * @class MyChannelInitializer
 * @package org.mindidea.netty.demo01
 * @date 2020/10/26 22:38
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.demo02;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/26 22:38
 * @Blog https://mindidea.org
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");

        // 在管道中添加自定义的接受数据的实现方法
        channel.pipeline().addLast(new MyServerHandler());
    }
}
