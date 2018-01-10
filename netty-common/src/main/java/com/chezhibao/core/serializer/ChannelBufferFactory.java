/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ChannelBufferFactory.java
 * Author:   zhangdanji
 * Date:     2017年10月11日
 * Description: ChannelBuffer工厂类  
 */
package com.chezhibao.core.serializer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import java.nio.ByteOrder;

/**
 * ChannelBuffer工厂类
 *
 * @author zhangdanji
 */
public class ChannelBufferFactory {

    //大端序列
    public static ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    /**
     * 获取新的channelBuffer
     * @return 新的channelBuffer
     *
     * **/
    public static ChannelBuffer getBuffer(){
        return ChannelBuffers.dynamicBuffer();
    }

    /**
     * 复制字节数组到新的channelBuffer里
     * @param bytes 字节数组
     * @return 封装字节数组的channelBuffer
     *
     * **/
    public static ChannelBuffer getBuffer(byte[] bytes){
        return ChannelBuffers.copiedBuffer(bytes);
    }
}
