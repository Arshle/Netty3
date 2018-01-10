/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ChatResponse.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 聊天类响应对象  
 */
package com.chezhibao.module.chat;

import com.chezhibao.core.serializer.Serializer;

/**
 * 聊天类响应对象
 *
 * @author zhangdanji
 */
public class ChatResponse extends Serializer {

    //发送者ID
    private int sendPlayerId;
    //玩家名
    private String sendPlayerName;
    //目标玩家
    private String targetPlayerName;
    //消息类型
    private int chatType;
    //消息
    private String message;

    @Override
    protected void read() {
        this.sendPlayerId = readInt();
        this.sendPlayerName = readString();
        this.targetPlayerName = readString();
        this.chatType = readInt();
        this.message = readString();
    }

    @Override
    protected void write() {
        writeInt(sendPlayerId);
        writeString(sendPlayerName);
        writeString(targetPlayerName);
        writeInt(chatType);
        writeString(message);
    }

    /*Getters、Setters*/
    public int getSendPlayerId() {
        return sendPlayerId;
    }

    public void setSendPlayerId(int sendPlayerId) {
        this.sendPlayerId = sendPlayerId;
    }

    public String getSendPlayerName() {
        return sendPlayerName;
    }

    public void setSendPlayerName(String sendPlayerName) {
        this.sendPlayerName = sendPlayerName;
    }

    public String getTargetPlayerName() {
        return targetPlayerName;
    }

    public void setTargetPlayerName(String targetPlayerName) {
        this.targetPlayerName = targetPlayerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }
}
