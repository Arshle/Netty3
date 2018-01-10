/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PlayerDao.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家dao类  
 */
package com.chezhibao.dao;

import com.chezhibao.entity.Player;

/**
 * 玩家dao类
 *
 * @author zhangdanji
 */
public interface PlayerDao {

    /**
     * 根据ID获取player
     * @param playerId 玩家ID
     * @return 玩家对象
     *
     * **/
    public Player getPlayerById(int playerId);

    /**
     * 根据名称获取玩家
     * @param playerName 玩家名称
     * @return 玩家对象
     *
     * **/
    public Player getPlayerByName(String playerName);

    /**
     * 创建玩家
     * @param player 玩家对象
     * @return 玩家对象
     *
     * **/
    public Player createPlayer(Player player);
}
