/*
 * @class MyServerHandler
 * @package org.mindidea.gg.demo05.heartbeat
 * @date 2020/11/1 0:14
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo05.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/1 0:14
 * @blog https://mindidea.org
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String eventType = "";
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    eventType = "read";
                    break;
                case WRITER_IDLE:
                    eventType = "write";
                    break;
                case ALL_IDLE:
                    eventType = "read&write";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "---" + eventType);
        }
    }
}
