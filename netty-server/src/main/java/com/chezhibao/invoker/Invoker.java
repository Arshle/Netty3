/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Invoker.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 方法执行器  
 */
package com.chezhibao.invoker;

import java.lang.reflect.Method;

/**
 * 方法执行器
 *
 * @author zhangdanji
 */
public class Invoker {

    private Method method;
    private Object target;

    /**
     * 静态构造方法
     * @param target 执行目标
     * @param method 方法
     * @return 执行器
     *
     * **/
    public static Invoker valueOf(Object target, Method method){
        Invoker invoker = new Invoker();
        invoker.setTarget(target);
        invoker.setMethod(method);
        return invoker;
    }

    /**
     * 执行目标方法
     * @param params 方法参数
     * @return 返回对象
     *
     * **/
    public Object invoke(Object... params){
        try {
            return method.invoke(target, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*Getters、Setters*/
    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
