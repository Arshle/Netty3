/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: LoginRequest.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 登陆请求类  
 */
package com.chezhibao.module.player;

import com.chezhibao.core.serializer.Serializer;

/**
 * 登陆请求类
 *
 * @author zhangdanji
 */
public class LoginRequest extends Serializer{

    //用户名
    private String playerName;

    //密码
    private String password;

    @Override
    protected void read() {
        this.playerName = readString();
        this.password = readString();
    }

    @Override
    protected void write() {
        writeString(this.playerName);
        writeString(this.password);
    }

    /*Getters、Setters*/
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
