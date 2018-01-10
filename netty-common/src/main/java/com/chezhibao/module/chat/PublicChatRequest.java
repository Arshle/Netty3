/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PublicChatRequest.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 广播聊天请求对象  
 */
package com.chezhibao.module.chat;

import com.chezhibao.core.serializer.Serializer;

/**
 * 广播聊天请求对象
 *
 * @author zhangdanji
 */
public class PublicChatRequest extends Serializer {

    //内容
    private String context;

    @Override
    protected void read() {
        this.context = readString();
    }

    @Override
    protected void write() {
        writeString(context);
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
