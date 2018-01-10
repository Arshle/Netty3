/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: SessionManager.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 会话管理对象  
 */
package com.chezhibao.core.session;

import com.chezhibao.core.constants.ResultCode;
import com.chezhibao.core.model.Response;
import com.chezhibao.core.serializer.Serializer;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 会话管理对象
 *
 * @author zhangdanji
 */
public class SessionManager {

    //在线的会话
    private static final ConcurrentMap<Integer, Session> onlineSessions = new ConcurrentHashMap<>();

    /**
     * 新加入会话
     * @param playerId 玩家id
     * @param session 会话
     * @return 是否成功
     *
     * **/
    public static boolean putSession(int playerId,Session session){
        if(!onlineSessions.containsKey(playerId)){
            boolean success = onlineSessions.putIfAbsent(playerId, session) != null;
            return success;
        }
        return false;
    }

    /**
     * 移除会话
     * @param playerId 玩家ID
     * @return 移除的会话
     *
     * **/
    public static Session removeSession(int playerId){
        return onlineSessions.remove(playerId);
    }

    /**
     * 发送消息
     * @param playerId 玩家ID
     * @param module 模块号
     * @
     *
     * **/
    public static <T extends Serializer> void sendMessage(int playerId,int module,int cmd,T message){
        Session session = onlineSessions.get(playerId);
        if(session != null && session.isConnected()){
            Response response = new Response(module,cmd,message.getBytes());
            response.setStateCode(ResultCode.SUCCESS);
            session.write(response);
        }
    }

    /**
     * 是否在线
     * @param playerId 玩家ID
     * @return 是否在线
     *
     * **/
    public static boolean isOnlinePlayer(int playerId){
        return onlineSessions.containsKey(playerId);
    }

    /**
     * 返回所有在线玩家ID
     * @return 所有在线玩家ID
     *
     * **/
    public static Set<Integer> getOnlinePlayers(){
        return Collections.unmodifiableSet(onlineSessions.keySet());
    }
}
