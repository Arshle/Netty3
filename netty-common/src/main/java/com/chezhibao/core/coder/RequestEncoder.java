/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: RequestEncoder.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 请求对象编码器  
 */
package com.chezhibao.core.coder;

import com.chezhibao.core.constants.Constants;
import com.chezhibao.core.model.Request;
import com.chezhibao.core.serializer.ChannelBufferFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * 请求对象编码器
 *
 * @author zhangdanji
 */
public class RequestEncoder extends OneToOneEncoder{

    @Override
    protected Object encode(ChannelHandlerContext channelHandlerContext, Channel channel, Object o) throws Exception {
        Request message = (Request) o;
        ChannelBuffer channelBuffer = ChannelBufferFactory.getBuffer();
        //包头
        channelBuffer.writeInt(Constants.HEAD_FLAG);
        //模块号
        channelBuffer.writeInt(message.getModule());
        //命令号
        channelBuffer.writeInt(message.getCmd());
        //数据长度
        int length = message.getData() == null ? 0 : message.getData().length;
        channelBuffer.writeInt(length);
        //长度大于0时写入数据
        if(length > 0){
            channelBuffer.writeBytes(message.getData());
        }
        return channelBuffer;
    }
}
