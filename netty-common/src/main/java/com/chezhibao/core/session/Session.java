/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Session.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 会话对象  
 */
package com.chezhibao.core.session;

/**
 * 会话对象
 *
 * @author zhangdanji
 */
public interface Session {

    /**
     * 会话绑定对象
     * @return 绑定的对象
     *
     * **/
    Object getAttachment();

    /**
     * 绑定会话对象
     * @param attachment 绑定对象
     *
     * **/
    void setAttachment(Object attachment);

    /**
     * 移除绑定对象
     *
     * **/
    void removeAttachment();

    /**
     * 向会话中写入消息
     * @param message 消息
     *
     * **/
    void write(Object message);

    /**
     * 判断会话是否在连接中
     * @return 是否连接
     *
     * **/
    boolean isConnected();

    /**
     * 关闭会话对象
     *
     * **/
    void close();
}
