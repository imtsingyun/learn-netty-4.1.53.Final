/*
 * @class MyTextWebSocketFrameHandler
 * @package org.mindidea.gg.demo05.websocket
 * @date 2020/11/1 1:02
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo05.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/1 1:02
 * @blog https://mindidea.org
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器收到消息：" + msg.text());

        String message = LocalDateTime.now() + "服务器返回: " + msg.text();
        // 回复浏览器
        ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandlerAdd: " + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandlerRemoved: " + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Error: " + cause.getMessage());
        ctx.channel().close();
    }
}
