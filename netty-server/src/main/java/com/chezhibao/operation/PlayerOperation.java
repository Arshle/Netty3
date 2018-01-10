/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PlayerOperation.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家操作类  
 */
package com.chezhibao.operation;

import com.chezhibao.core.annotion.SocketCmd;
import com.chezhibao.core.annotion.SocketModule;
import com.chezhibao.core.model.Result;
import com.chezhibao.core.session.Session;
import com.chezhibao.module.constants.Module;
import com.chezhibao.module.constants.Player;
import com.chezhibao.module.player.PlayerResponse;

/**
 * 玩家操作类
 *
 * @author zhangdanji
 */
@SocketModule(module = Module.PLAYER)
public interface PlayerOperation {

    /**
     * 登陆并注册
     * @param session 会话
     * @param data 数据
     * @return 登陆结果
     *
     * **/
    @SocketCmd(cmd = Player.REGISTER_AND_LOGIN)
    public Result<PlayerResponse> registerAndLogin(Session session, byte[] data);

    /**
     * 登陆
     * @param session 会话
     * @param data 数据
     * @return 登陆结果
     *
     * **/
    @SocketCmd(cmd = Player.LOGIN)
    public Result<PlayerResponse> login(Session session, byte[] data);
}
