/*
 * @class GroupChatClient
 * @package org.mindidea.gg.demo05.groupchat
 * @date 2020/10/31 18:42
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo05.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.internal.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/31 18:42
 * @blog https://mindidea.org
 */
public class GroupChatClient {

    private final String host;
    private final int port;

    public static final Map<String, String> map = new HashMap<>();

    public static final Map<String, String> ipMapId = new HashMap<>();

    public GroupChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run(String id) throws InterruptedException {
        EventLoopGroup ex = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(ex)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });

            AttributeKey<String> key = AttributeKey.valueOf("thiskey");
            ChannelFuture channelFuture = bootstrap.attr(key, id).connect(host, port).sync();
            ipMapId.put(channelFuture.channel().localAddress().toString(), id);

            System.out.println("===============" + channelFuture.channel().localAddress() + "登录成功 ===============");

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channelFuture.channel().writeAndFlush(msg + "\r\n");
            }

        } finally {
            ex.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        boolean hasLogin = false;

        String id = "";
        String pwd = "";


        while (!hasLogin) {
            System.out.println("1. 登录\t2. 注册 [请输入1或2]");

            String msg = scanner.nextLine();
            if ("1".equals(msg)) {
                System.out.println("输入登录id：");
                if (scanner.hasNextLine()) {
                    id = scanner.nextLine();
                }
                System.out.println("输入登录密码：");
                if (scanner.hasNextLine()) {
                    pwd = scanner.nextLine();
                }
                if (StringUtil.isNullOrEmpty(id) || StringUtil.isNullOrEmpty(pwd) ) {
                    System.out.println("id和密码不能为空");
                    id = "";
                    pwd = "";
                    continue;
                }
                if (!pwd.equals(map.get(id))) {
                    System.out.println("id或密码有误！");
                    id = "";
                    pwd = "";
                    continue;
                }
                break;
            } else if ("2".equals(msg)) {
                System.out.println("================ 注册 ================");
                System.out.println("请输入ID:");

                if (scanner.hasNextLine()) {
                    id = scanner.nextLine();
                }
                if (StringUtil.isNullOrEmpty(id)) {
                    System.out.println("id不能为空");
                    id = "";
                    pwd = "";
                    continue;
                }
                if (map.get(id) != null) {
                    System.out.println("id已存在");
                    id = "";
                    pwd = "";
                    continue;
                }
                System.out.println("请输入密码:");
                if (scanner.hasNextLine()) {
                    pwd = scanner.nextLine();
                }
                if (StringUtil.isNullOrEmpty(pwd)) {
                    System.out.println("密码不能为空");
                    id = "";
                    pwd = "";
                    continue;
                }
                map.put(id, pwd);
                System.out.println("================ 注册成功，请输入1进行登录 ================");
                continue;
            } else if (!StringUtil.isNullOrEmpty(msg)){
                System.out.println("请输入1或2");
                continue;
            }
        }


        new GroupChatClient("127.0.0.1", 6688).run(id);
    }
}
