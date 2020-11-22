/*
 * @class HelloServiceImpl
 * @package org.mindidea.gg.dobbo.provider
 * @date 2020/11/8 22:01
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.dobbo.provider;

import io.netty.util.internal.StringUtil;
import org.mindidea.gg.dobbo.common.HelloService;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/8 22:01
 * @blog https://mindidea.org
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String mes) {
        System.out.println("收到客户端消息：" + mes);
        if (StringUtil.isNullOrEmpty(mes)) {
            return "已收到消息【" + mes + "】";
        }
        return "已收到消息";
    }
}
