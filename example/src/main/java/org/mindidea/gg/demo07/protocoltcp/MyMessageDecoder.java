/*
 * @class MyMessageDecoder
 * @package org.mindidea.gg.demo07.protocoltcp
 * @date 2020/11/1 23:54
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo07.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/1 23:54
 * @blog https://mindidea.org
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder.decode() 被调用");

        MessageProtocol messageProtocol = new MessageProtocol();
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);

        messageProtocol.setContent(content);
        messageProtocol.setLen(len);

        out.add(messageProtocol);
    }
}
