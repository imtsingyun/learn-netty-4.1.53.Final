/*
 * @class MyServerHandler
 * @package org.mindidea.gg.demo07.tcp
 * @date 2020/11/1 19:48
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo07.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/1 19:48
 * @blog https://mindidea.org
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);


        String message = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("服务器接受到的数据：" + message);
        System.out.println("服务器接收到的消息量：" + (++this.count));


        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString() + "\n", CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }
}
