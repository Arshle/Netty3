/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ResponseEncoder.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 响应对象编码器  
 */
package com.chezhibao.core.coder;

import com.chezhibao.core.constants.Constants;
import com.chezhibao.core.model.Response;
import com.chezhibao.core.serializer.ChannelBufferFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * 响应对象编码器
 *
 * @author zhangdanji
 */
public class ResponseEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext channelHandlerContext, Channel channel, Object o) throws Exception {
        Response message = (Response) o;
        ChannelBuffer channelBuffer = ChannelBufferFactory.getBuffer();
        //包头
        channelBuffer.writeInt(Constants.HEAD_FLAG);
        //module和cmd
        channelBuffer.writeInt(message.getModule());
        channelBuffer.writeInt(message.getCmd());
        //结果码
        channelBuffer.writeInt(message.getStateCode());
        //数据长度和内容
        int length = message.getData() == null ? 0 : message.getData().length;
        channelBuffer.writeInt(length);
        if(length > 0){
            channelBuffer.writeBytes(message.getData());
        }
        return channelBuffer;
    }
}
