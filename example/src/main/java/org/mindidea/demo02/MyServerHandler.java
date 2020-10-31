/*
 * @class MyServerHandler
 * @package org.mindidea.netty.demo02
 * @date 2020/10/26 23:09
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.demo02;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/26 23:09
 * @Blog https://mindidea.org
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bf = (ByteBuf) msg;
        byte[] msgByte = new byte[bf.readableBytes()];
        bf.readBytes(msgByte);
        System.out.println(LocalDateTime.now() + " 接收到消息");
        System.out.println(new String(msgByte, StandardCharsets.UTF_8));
    }
}
