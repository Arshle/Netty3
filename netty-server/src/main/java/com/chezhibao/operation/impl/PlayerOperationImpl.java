/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: PlayerOperationImpl.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 玩家操作类实现  
 */
package com.chezhibao.operation.impl;

import com.chezhibao.core.constants.ResultCode;
import com.chezhibao.core.exception.ErrorCodeException;
import com.chezhibao.core.model.Result;
import com.chezhibao.core.session.Session;
import com.chezhibao.module.player.LoginRequest;
import com.chezhibao.module.player.PlayerResponse;
import com.chezhibao.module.player.RegisterRequest;
import com.chezhibao.operation.PlayerOperation;
import com.chezhibao.service.player.PlayerService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 玩家操作类实现
 *
 * @author zhangdanji
 */
public class PlayerOperationImpl implements PlayerOperation {

    @Resource
    private PlayerService playerService;

    @Override
    public Result<PlayerResponse> registerAndLogin(Session session, byte[] data) {
        PlayerResponse result = null;
        try {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.readFromBytes(data);
            //参数判空
            if(StringUtils.isEmpty(registerRequest.getPlayerName()) || StringUtils.isEmpty(registerRequest.getPassword())){
                return Result.ERROR(ResultCode.PLAYERNAME_NULL);
            }
            result = playerService.registerAndLogin(session, registerRequest.getPlayerName(), registerRequest.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.SUCCESS(result);
    }

    @Override
    public Result<PlayerResponse> login(Session session, byte[] data) {
        PlayerResponse result = null;
        try {
            //反序列化
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.readFromBytes(data);

            //参数判空
            if(StringUtils.isEmpty(loginRequest.getPlayerName()) || StringUtils.isEmpty(loginRequest.getPassword())){
                return Result.ERROR(ResultCode.PLAYERNAME_NULL);
            }

            //执行业务
            result = playerService.login(session, loginRequest.getPlayerName(), loginRequest.getPassword());
        } catch (ErrorCodeException e) {
            return Result.ERROR(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
        }
        return Result.SUCCESS(result);
    }
}
