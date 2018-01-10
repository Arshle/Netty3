/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Request.java
 * Author:   zhangdanji
 * Date:     2017年10月11日
 * Description: 请求对象模型  
 */
package com.chezhibao.core.model;

/**
 * 请求对象模型
 *
 * @author zhangdanji
 */
public class Request {

    //模块编号
    private int module;
    //命令号
    private int cmd;
    //数据内容
    private byte[] data;

    //私有化构造函数
    private Request(int module,int cmd,byte[] data){
        this.module = module;
        this.cmd = cmd;
        this.data = data;
    }

    //公共构造方法
    public static Request valueOf(int module,int cmd,byte[] data){
        return new Request(module, cmd, data);
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
