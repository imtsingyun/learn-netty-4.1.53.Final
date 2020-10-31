/*
 * @class NettyByteBuf02
 * @package org.mindidea.gg.demo04
 * @date 2020/10/31 17:58
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/31 17:58
 * @blog https://mindidea.org
 */
public class NettyByteBuf02 {

    public static void main(String[] args) {

        ByteBuf buf = Unpooled.copiedBuffer("Hello, world.", CharsetUtil.UTF_8);

        if (buf.hasArray()) {
            byte[] array = buf.array();
            System.out.println(new String(array, StandardCharsets.UTF_8));

            System.out.println("buf = " + buf);
        }
    }
}
