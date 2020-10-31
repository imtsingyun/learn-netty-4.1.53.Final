/*
 * @class TestServerInitializer
 * @package org.mindidea.gg.demo03.http
 * @date 2020/10/28 22:43
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo03.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/28 22:43
 * @blog https://mindidea.org
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 加入一个 netty 提供的 HttpServerCodec 编-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // 增加自定义 handler
        pipeline.addLast(new TestHttpServerHandler());
    }
}
