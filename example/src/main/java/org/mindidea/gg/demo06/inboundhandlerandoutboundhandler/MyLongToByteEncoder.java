/*
 * @class MyLongToByteEncoder
 * @package org.mindidea.gg.demo06.inboundhandlerandoutboundhandler
 * @date 2020/11/1 17:24
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo06.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/1 17:24
 * @blog https://mindidea.org
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder.encode");
        System.out.println("msg = " + msg);
        out.writeLong(msg);
    }
}
