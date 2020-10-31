/*
 * @class BasicBuffer
 * @package org.mindidea.gg.demo01
 * @date 2020/10/26 23:18
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo01;

import java.nio.IntBuffer;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/10/26 23:18
 * @blog https://mindidea.org
 */
public class BasicBuffer {

    public static void main(String[] args) {
        // buffer 的使用
        // 创建一个 Buffer，大小为 5，可以存放 5 个 int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        // 存入数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 从 buffer 中读取数据前，将 buffer 进行读取切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
