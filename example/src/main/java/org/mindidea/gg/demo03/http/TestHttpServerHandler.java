/*
 * @class TestHttpServerHandler
 * @package org.mindidea.gg.demo03.http
 * @date 2020/10/28 22:42
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo03.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/28 22:42
 * @blog https://mindidea.org
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {
            System.out.println("pipeline hashCode: " + ctx.pipeline().hashCode());
            System.out.println("msg type = " + msg.getClass());
            System.out.println("客户端地址 " + ctx.channel().remoteAddress());


            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico");
                return;
            }

            ByteBuf content = Unpooled.copiedBuffer("Hello, 我是服务器", CharsetUtil.UTF_8);

            DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            resp.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());


            ctx.writeAndFlush(resp);
        }
    }
}
