/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PlayerServiceImpl.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家service实现类  
 */
package com.chezhibao.service.impl;

import com.chezhibao.core.constants.ResultCode;
import com.chezhibao.core.exception.ErrorCodeException;
import com.chezhibao.core.session.Session;
import com.chezhibao.core.session.SessionManager;
import com.chezhibao.dao.PlayerDao;
import com.chezhibao.entity.Player;
import com.chezhibao.module.player.PlayerResponse;
import com.chezhibao.service.player.PlayerService;

import javax.annotation.Resource;

/**
 * 玩家service实现类
 *
 * @author zhangdanji
 */
public class PlayerServiceImpl implements PlayerService {

    @Resource
    private PlayerDao playerDao;

    @Override
    public PlayerResponse registerAndLogin(Session session, String playerName, String password) {
        Player existplayer = playerDao.getPlayerByName(playerName);
        if(existplayer != null){
            throw new ErrorCodeException(ResultCode.PLAYER_EXIST);
        }
        Player player = new Player();
        player.setPlayerName(playerName);
        player.setPassword(password);
        player = playerDao.createPlayer(player);

        return login(session,player.getPlayerName(),player.getPassword());
    }

    @Override
    public PlayerResponse login(Session session, String playerName, String password) {

        // 判断当前会话是否已登录
        if (session.getAttachment() != null) {
            throw new ErrorCodeException(ResultCode.HAS_LOGIN);
        }

        // 玩家不存在
        Player player = playerDao.getPlayerByName(playerName);
        if (player == null) {
            throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
        }

        // 密码错误
        if (!player.getPassword().equals(password)) {
            throw new ErrorCodeException(ResultCode.PASSWARD_ERROR);
        }

        // 判断是否在其他地方登录过
        boolean onlinePlayer = SessionManager.isOnlinePlayer(player.getPlayerId());
        if (onlinePlayer) {
            Session oldSession = SessionManager.removeSession(player.getPlayerId());
            oldSession.removeAttachment();
            // 踢下线
            oldSession.close();
        }

        // 加入在线玩家会话
        if (SessionManager.putSession(player.getPlayerId(), session)) {
            session.setAttachment(player);
        } else {
            throw new ErrorCodeException(ResultCode.LOGIN_FAIL);
        }

        // 创建Response传输对象返回
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setPlayerId(player.getPlayerId());
        playerResponse.setPlayerName(player.getPlayerName());
        playerResponse.setLevel(player.getLevel());
        playerResponse.setExp(player.getExp());
        return playerResponse;
    }
}
