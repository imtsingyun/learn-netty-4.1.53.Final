/*
 * @class NettyServerHandler
 * @package org.mindidea.gg.demo02
 * @date 2020/10/28 19:54
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo02;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 自定义 Handler
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/28 19:54
 * @blog https://mindidea.org
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据的事件（可以读取客户端发送的消息）
     * @author Tsingyun(青雲)
     * @createTime 2020/10/28 19:56
     * @param ctx 上下文，含有管道 pipeline, 通道 channel, 地址
     * @param msg 客户端发送的数据
     * @return void
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        System.out.println("server thread: " + Thread.currentThread().getName());
//        System.out.println("server ctx = " + ctx);
//        // 将 msg 转成 ByteBuf (netty提供的)
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("msg from client: " + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("addr of client: " + ctx.channel().remoteAddress());

        // 耗时的任务 - 异步处理，提交该 channel 对应的 NioEventLoop 的 taskQueue 中
        // 1. 用户自定义普通任务
//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10 * 1000);
//                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端2\n", CharsetUtil.UTF_8));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


        // 2. 用户自定义定时任务  提交到 scheduleTaskQueue
        ctx.channel().eventLoop().schedule((Runnable) () -> {
            try {
                Thread.sleep(10 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端2\n", CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 5, TimeUnit.SECONDS);

        System.out.println("go on...");

    }

    /**
     * 读取数据完毕
     * @author Tsingyun(青雲)
     * @createTime 2020/10/28 20:04
     * @param ctx
     * @return void
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写入到缓存，并刷新
        // 对要发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client!\n", CharsetUtil.UTF_8));
    }

    /**
     * 发生异常时需要关闭通道
     * @author Tsingyun(青雲)
     * @createTime 2020/10/28 20:20
     * @param ctx 上下文
     * @return void
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
