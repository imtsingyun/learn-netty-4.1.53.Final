/*
 * @class NettyClientHandler
 * @package org.mindidea.gg.demo02
 * @date 2020/10/28 20:33
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo06.codec2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/28 20:33
 * @blog https://mindidea.org
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪触发该方法
     *
     * @param ctx
     * @return void
     * @author Tsingyun(青雲)
     * @createTime 2020/10/28 20:35
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 发送一个 Student 或 Worker 对象
        int random = new Random().nextInt(2);
        System.out.println(random);
        MyDataInfo.MyMessage myMessage;

        if (0 == random) {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDateType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(4).setName("老王").build())
                    .build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDateType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setAge(23).setName("张三").build())
                    .build();
        }
        ctx.writeAndFlush(myMessage);
    }

    /**
     * 当通道有读取事件时，会触发该方法
     *
     * @param ctx
     * @return void
     * @author Tsingyun(青雲)
     * @createTime 2020/10/28 20:43
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
