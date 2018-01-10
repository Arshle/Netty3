/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Response.java
 * Author:   zhangdanji
 * Date:     2017年10月11日
 * Description: 返回对象模型  
 */
package com.chezhibao.core.model;

import com.chezhibao.core.constants.ResultCode;

/**
 * 返回对象模型
 *
 * @author zhangdanji
 */
public class Response {

    //模块号
    private int module;
    //命令号
    private int cmd;
    //结果码
    private int stateCode = ResultCode.SUCCESS;
    //数据
    private byte[] data;

    public Response(Request message) {
        this.module = message.getModule();
        this.cmd = message.getCmd();
    }

    public Response(int module, int cmd, byte[] data){
        this.module = module;
        this.cmd = cmd;
        this.data = data;
    }

    /*Getters、Setters*/
    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
