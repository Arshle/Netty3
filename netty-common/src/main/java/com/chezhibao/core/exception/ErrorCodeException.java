/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: ErrorCodeException.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 错误代码异常类  
 */
package com.chezhibao.core.exception;

/**
 * 错误代码异常类
 *
 * @author zhangdanji
 */
public class ErrorCodeException extends RuntimeException {

    private static final long serialVersionUID = 3569750583522313437L;

    //错误代码
    private final int errorCode;

    public ErrorCodeException(int errorCode){
        this.errorCode = errorCode;
    }

    public int getErrorCode(){
        return errorCode;
    }
}
