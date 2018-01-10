/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ChatService.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 聊天service类  
 */
package com.chezhibao.service.chat;

/**
 * 聊天service类
 *
 * @author zhangdanji
 */
public interface ChatService {

    /**
     * 群发消息
     * @param playerId
     * @param content
     */
    public void publicChat(int playerId, String content);


    /**
     * 私聊
     * @param playerId
     * @param targetPlayerId
     * @param content
     */
    public void privateChat(int playerId, int targetPlayerId, String content);
}
