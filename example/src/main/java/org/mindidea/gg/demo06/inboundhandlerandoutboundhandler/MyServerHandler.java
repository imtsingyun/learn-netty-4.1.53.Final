/*
 * @class MyServerHandler
 * @package org.mindidea.gg.demo06.inboundhandlerandoutboundhandler
 * @date 2020/11/1 16:53
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo06.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/1 16:53
 * @blog https://mindidea.org
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("从客户端读取的数据：" + msg);
    }
}
