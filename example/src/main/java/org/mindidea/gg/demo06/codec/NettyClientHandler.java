/*
 * @class NettyClientHandler
 * @package org.mindidea.gg.demo02
 * @date 2020/10/28 20:33
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo06.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/28 20:33
 * @blog https://mindidea.org
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪触发该方法
     * @author Tsingyun(青雲)
     * @createTime 2020/10/28 20:35
     * @param ctx
     * @return void
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 发送一个 Student 对象
        StudentPOJO.Student.Builder student = StudentPOJO.Student.newBuilder();
        student.setId(3).setName("测试");

        ctx.writeAndFlush(student);
    }

    /**
     * 当通道有读取事件时，会触发该方法
     * @author Tsingyun(青雲)
     * @createTime 2020/10/28 20:43
     * @param ctx
     * @return void
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("msg from server: " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("addr of server: " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
