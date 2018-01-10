/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: SessionImpl.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 会话实现对象  
 */
package com.chezhibao.core.session;

import org.jboss.netty.channel.Channel;

/**
 * 会话实现对象
 *
 * @author zhangdanji
 */
public class SessionImpl implements Session {

    //管道对象
    private Channel channel;

    public SessionImpl(Channel channel){
        this.channel = channel;
    }

    /**
     * 会话绑定对象
     * @return 绑定的对象
     *
     * **/
    @Override
    public Object getAttachment() {
        return channel.getAttachment();
    }

    /**
     * 绑定会话对象
     * @param attachment 绑定对象
     *
     * **/
    @Override
    public void setAttachment(Object attachment) {
        channel.setAttachment(attachment);
    }

    /**
     * 移除绑定对象
     *
     * **/
    @Override
    public void removeAttachment() {
        channel.setAttachment(null);
    }

    /**
     * 向会话中写入消息
     * @param message 消息
     *
     * **/
    @Override
    public void write(Object message) {
        channel.write(message);
    }

    /**
     * 判断会话是否在连接中
     * @return 是否连接
     *
     * **/
    @Override
    public boolean isConnected() {
        return channel.isConnected();
    }

    /**
     * 关闭会话对象
     *
     * **/
    @Override
    public void close() {
        channel.close();
    }
}
