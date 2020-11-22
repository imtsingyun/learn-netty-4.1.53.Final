/*
 * @class NettyServerHandler
 * @package org.mindidea.gg.dobbo.netty
 * @date 2020/11/8 22:11
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.dobbo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.mindidea.gg.dobbo.provider.HelloServiceImpl;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/8 22:11
 * @blog https://mindidea.org
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送的消息
        System.out.println("msg = " + msg);
        if (msg.toString().startsWith("HelloService#hello#")) {
            String result =
                    new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
