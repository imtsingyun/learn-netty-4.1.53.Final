/*
 * @class ServerBootstrap
 * @package org.mindidea.gg.dobbo.provider
 * @date 2020/11/8 22:03
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.dobbo.provider;

import org.mindidea.gg.dobbo.netty.NettyServer;

/**
 * 启动一个服务提供者
 *
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/8 22:03
 * @blog https://mindidea.org
 */
public class ServerBootstrap {

    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 6688);
    }
}
