/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Result.java
 * Author:   zhangdanji
 * Date:     2017年10月11日
 * Description:   
 */
package com.chezhibao.core.model;

import com.chezhibao.core.constants.ResultCode;
import com.chezhibao.core.serializer.Serializer;

/**
 * @author zhangdanji
 */
public class Result<T extends Serializer> {

    //结果码
    private int resultCode;

    //结果内容
    private T content;

    /**
     * 返回成功的result对象
     * @param content 成功内容
     * @return result对象
     *
     * **/
    public static <T extends Serializer> Result<T> SUCCESS(T content){
        Result<T> result = new Result<>();
        result.resultCode = ResultCode.SUCCESS;
        result.content = content;
        return result;
    }

    /**
     * 返回成功的result对象
     * @return result对象
     *
     * **/
    public static <T extends Serializer> Result<T> SUCCESS(){
        Result<T> result = new Result<T>();
        result.resultCode = ResultCode.SUCCESS;
        return result;
    }

    /**
     * 错误对象
     * @param resultCode 结果码
     * @return result对象
     *
     * **/
    public static <T extends Serializer> Result<T> ERROR(int resultCode){
        Result<T> result = new Result<T>();
        result.resultCode = resultCode;
        return result;
    }

    /**
     * 构造一个result对象
     * @param resultCode 结果码
     * @param content 内容
     * @return result对象
     *
     * **/
    public static <T extends Serializer> Result<T> valueOf(int resultCode, T content){
        Result<T> result = new Result<T>();
        result.resultCode = resultCode;
        result.content = content;
        return result;
    }

    /**
     * 结果是否成功
     * @return 是否成功
     *
     * **/
    public boolean isSuccess(){
        return this.resultCode == ResultCode.SUCCESS;
    }

    /*Getters、Setters*/
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
