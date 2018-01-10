/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: HandlerScanner.java
 * Author:   zhangdanji
 * Date:     2017年10月12日
 * Description: 属性扫描器  
 */
package com.chezhibao.scanner;

import com.chezhibao.core.annotion.SocketCmd;
import com.chezhibao.core.annotion.SocketModule;
import com.chezhibao.invoker.Invoker;
import com.chezhibao.invoker.InvokerHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

/**
 * 属性扫描器
 *
 * @author zhangdanji
 */
@Component("handlerScanner")
public class HandlerScanner implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //获取当前类所有接口
        Class<?> clazz = bean.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();
        if(interfaces != null && interfaces.length > 0){
            for(Class<?> inter : interfaces){
                //找到模块号注解,如果为空则跳过接口
                SocketModule socketModule = inter.getAnnotation(SocketModule.class);
                if(socketModule == null){
                    continue;
                }
                //找到所有方法
                Method[] methods = inter.getMethods();
                if(methods != null && methods.length > 0){
                    for(Method method : methods){
                        //找到命令号注解，如果为空则跳过方法
                        SocketCmd socketCmd = method.getAnnotation(SocketCmd.class);
                        if(socketCmd == null){
                            continue;
                        }

                        int module = socketModule.module();
                        int cmd = socketCmd.cmd();

                        //不存在执行器再添加,否则报错
                        if(InvokerHolder.getInvoker(module,cmd) == null){
                            InvokerHolder.addInvoker(module,cmd, Invoker.valueOf(bean,method));
                        }else{
                            throw new RuntimeException("Duplicate Invoker");
                        }
                    }
                }
            }
        }
        return bean;
    }
}
