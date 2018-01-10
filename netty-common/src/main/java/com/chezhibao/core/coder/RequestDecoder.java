/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: RequestDecoder.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 请求解码器  
 */
package com.chezhibao.core.coder;

import com.chezhibao.core.constants.Constants;
import com.chezhibao.core.model.Request;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * 请求解码器
 *
 * @author zhangdanji
 */
public class RequestDecoder extends FrameDecoder {

    //包头+模块号+命令号+数据长度最少长度16
    private static final int BASE_LENGTH = 4 + 4 + 4 + 4;

    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, ChannelBuffer channelBuffer) throws Exception {
        if(channelBuffer.readableBytes() > 0){
            int beginIndex;

            while(true){
                //记录下开始读取的节点
                beginIndex = channelBuffer.readerIndex();
                //标记起始的读标记
                channelBuffer.markReaderIndex();
                //如果找到包头标识则结束循环开始读取数据
                if(channelBuffer.readInt() == Constants.HEAD_FLAG){
                    break;
                }

                //没找到包头跳过一个字节
                channelBuffer.resetReaderIndex();
                channelBuffer.readByte();

                //不满足情况直接跳过
                if(channelBuffer.readableBytes() < BASE_LENGTH){
                    return null;
                }
            }

            //模块号
            int module = channelBuffer.readInt();
            //命令号
            int cmd = channelBuffer.readInt();
            //长度
            int length = channelBuffer.readInt();
            //如果长度小于0则直接关闭通道
            if(length < 0){
                channel.close();
            }
            //数据包没到齐先缓存channelBuffer
            if(channelBuffer.readableBytes() < length){
                channelBuffer.readerIndex(beginIndex);
                return null;
            }

            //数据部分
            byte[] data = new byte[length];
            channelBuffer.readBytes(data);

            //返回封装的Request对象
            return Request.valueOf(module, cmd, data);

        }
        return null;
    }
}
