/*
 * @class MyServerHandler
 * @package org.mindidea.gg.demo07.tcp
 * @date 2020/11/1 19:48
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo07.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/1 19:48
 * @blog https://mindidea.org
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("服务端接收到的信息如下");
        System.out.println("len=" + len);
        System.out.println("content=" + new String(content, CharsetUtil.UTF_8));

        System.out.println("服务器接收到的消息包数量=" + (++this.count));

    }
}
