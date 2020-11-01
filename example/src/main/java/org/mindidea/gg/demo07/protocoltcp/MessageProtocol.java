/*
 * @class MessageProtocol
 * @package org.mindidea.gg.demo07.protocoltcp
 * @date 2020/11/1 20:54
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.gg.demo07.protocoltcp;

/**
 * 自定义协议包
 *
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/1 20:54
 * @blog https://mindidea.org
 */
public class MessageProtocol {

    private int len;

    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
