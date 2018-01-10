/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ResponseDecoder.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 响应对象解码器  
 */
package com.chezhibao.core.coder;

import com.chezhibao.core.constants.Constants;
import com.chezhibao.core.model.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * 响应对象解码器
 *
 * @author zhangdanji
 */
public class ResponseDecoder extends FrameDecoder {

    private static final int BASE_LENGTH = 4 + 4 + 4 + 4;

    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, ChannelBuffer channelBuffer) throws Exception {

        if(channelBuffer.readableBytes() >= BASE_LENGTH){
            int beginIndex;

            while(true){
                //buffer读指针
                beginIndex = channelBuffer.readerIndex();
                //标记读指针
                channelBuffer.markReaderIndex();
                //找到包头标识则退出循环
                if(channelBuffer.readInt() == Constants.HEAD_FLAG){
                    break;
                }

                //没找到包头标识则跳过一个字节
                channelBuffer.resetReaderIndex();
                channelBuffer.readByte();

                //数据不完整先缓存
                if(channelBuffer.readableBytes() < BASE_LENGTH){
                    return null;
                }
            }

            //模块号
            int module = channelBuffer.readInt();
            //命令号
            int cmd = channelBuffer.readInt();
            //结果码
            int stateCode = channelBuffer.readInt();
            //数据长度
            int length = channelBuffer.readInt();
            if(length < 0){
                channel.close();
            }
            //数据包不完整，重置读指针后缓存
            if(channelBuffer.readableBytes() < length){
                channelBuffer.readerIndex(beginIndex);
                return null;
            }

            //数据内容
            byte[] data = new byte[length];
            channelBuffer.readBytes(data);

            //响应对象
            Response message = new Response(module,cmd,data);
            message.setStateCode(stateCode);
            return message;
        }
        return null;
    }
}
