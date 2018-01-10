/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ChatServiceImpl.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 聊天service实现类  
 */
package com.chezhibao.service.impl;

import com.chezhibao.core.constants.ResultCode;
import com.chezhibao.core.exception.ErrorCodeException;
import com.chezhibao.core.session.SessionManager;
import com.chezhibao.dao.PlayerDao;
import com.chezhibao.entity.Player;
import com.chezhibao.module.chat.ChatResponse;
import com.chezhibao.module.constants.Chat;
import com.chezhibao.module.constants.Module;
import com.chezhibao.service.chat.ChatService;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 聊天service实现类
 *
 * @author zhangdanji
 */
public class ChatServiceImpl implements ChatService {

    @Resource
    private PlayerDao playerDao;

    @Override
    public void publicChat(int playerId, String content) {
        Player player = playerDao.getPlayerById(playerId);


        //获取所有在线玩家
        Set<Integer> onlinePlayers = SessionManager.getOnlinePlayers();

        //创建消息对象
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setChatType(Chat.PUBLIC_CHAT);
        chatResponse.setSendPlayerId(player.getPlayerId());
        chatResponse.setSendPlayerName(player.getPlayerName());
        chatResponse.setMessage(content);

        //发送消息
        for(int targetPlayerId : onlinePlayers){
            SessionManager.sendMessage(targetPlayerId, Module.CHAT, Chat.PUSHCHAT, chatResponse);
        }
    }

    @Override
    public void privateChat(int playerId, int targetPlayerId, String content) {
        //不能和自己私聊
        if(playerId == targetPlayerId){
            throw new ErrorCodeException(ResultCode.CAN_CHAT_YOUSELF);
        }

        Player player = playerDao.getPlayerById(playerId);

        //判断目标是否存在
        Player targetPlayer = playerDao.getPlayerById(targetPlayerId);
        if(targetPlayer == null){
            throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
        }

        //判断对方是否在线
        if(!SessionManager.isOnlinePlayer(targetPlayerId)){
            throw new ErrorCodeException(ResultCode.PLAYER_NO_ONLINE);
        }

        //创建消息对象
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setChatType(Chat.PRIVATE_CHAT);
        chatResponse.setSendPlayerId(player.getPlayerId());
        chatResponse.setSendPlayerName(player.getPlayerName());
        chatResponse.setTargetPlayerName(targetPlayer.getPlayerName());
        chatResponse.setMessage(content);

        //给目标对象发送消息
        SessionManager.sendMessage(targetPlayerId, Module.CHAT, Chat.PUSHCHAT, chatResponse);
        //给自己也回一个(偷懒做法)
        SessionManager.sendMessage(playerId, Module.CHAT, Chat.PUSHCHAT, chatResponse);
    }
}
