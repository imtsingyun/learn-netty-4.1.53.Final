/*
 * @class MyClientHandler
 * @package org.mindidea.gg.demo07.tcp
 * @date 2020/11/1 19:44
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo07.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 *
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/1 19:44
 * @blog https://mindidea.org
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String msg = "我是一条测试数据";
            byte[] bytes = msg.getBytes(CharsetUtil.UTF_8);
            int length = msg.getBytes(CharsetUtil.UTF_8).length;

            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(bytes);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息：" + cause.getMessage());
        ctx.close();
    }
}
