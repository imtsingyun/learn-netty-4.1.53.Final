/*
 * @class MyClientHeandler
 * @package org.mindidea.gg.demo06.inboundhandlerandoutboundhandler
 * @date 2020/11/1 17:26
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo06.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/1 17:26
 * @blog https://mindidea.org
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");
        ctx.writeAndFlush(123456L);
    }
}
