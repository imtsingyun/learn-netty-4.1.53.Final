/*
 * @class NettyByteBuf01
 * @package org.mindidea.gg.demo04
 * @date 2020/10/31 17:00
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/31 17:00
 * @blog https://mindidea.org
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {

        // 创建一个 ByteBuf
        // buf 对象包含一个数组 byte[10]
        // netty 的 buf 中不需要 flip 进行反转，底层维护了 readerIndex 和 writerIndex
        ByteBuf buf = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buf.writeByte(i);
        }

        for (int i = 0; i < buf.capacity(); i++) {
            System.out.println(buf.readByte());
        }
        System.out.println("执行完毕");
    }
}
