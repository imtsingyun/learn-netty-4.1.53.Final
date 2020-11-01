/*
 * @class MyServerInitializer
 * @package org.mindidea.gg.demo06.inboundhandlerandoutboundhandler
 * @date 2020/11/1 16:48
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo06.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/1 16:48
 * @blog https://mindidea.org
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new MyByteToLongDecoder());
        pipeline.addLast(new MyServerHandler());
    }
}
