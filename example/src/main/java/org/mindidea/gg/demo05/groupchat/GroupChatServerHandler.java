/*
 * @class GroupChatServerHandler
 * @package org.mindidea.gg.demo05.groupchat
 * @date 2020/10/31 18:17
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo05.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/31 18:17
 * @blog https://mindidea.org
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 定义一个 channel 组，管理所有 channel
    // GlobalEventExecutor.INSTANCE 是全局事件执行器，是单例
    private static ChannelGroup channels =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        AttributeKey<String> key = AttributeKey.valueOf("thiskey");
        String s = channel.attr(key).get();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        AttributeKey<String> key = AttributeKey.valueOf("thiskey");
        String s = channel.attr(key).get();

        // 将客户端加入的信息推送给其他客户端
        channels.writeAndFlush(localDateTime() + "「客户端」" + channel.remoteAddress() + "加入聊天\n");
        channels.add(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LocalDateTime now = LocalDateTime.now();

        System.out.println(localDateTime() + ctx.channel().remoteAddress() + "上线了...");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(localDateTime() + ctx.channel().remoteAddress() + "离线了...");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.writeAndFlush(localDateTime() + "「客户端」" + ctx.channel().remoteAddress() + "离开了\n");
        System.out.println(localDateTime() + "当前在线数：" + channels.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel channel = ctx.channel();

        channels.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(localDateTime() + "「客户端」" + channel.remoteAddress() + "发送了消息: " + msg + "\n");
            } else {
                ch.writeAndFlush(localDateTime() + "「自己」发送了消息: " + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private String localDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(dtf) + " ";
    }
}
