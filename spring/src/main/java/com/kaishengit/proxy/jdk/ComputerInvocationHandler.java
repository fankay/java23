package com.kaishengit.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by fankay on 2017/7/10.
 */
public class ComputerInvocationHandler implements InvocationHandler {

    //指定目标对象
    private Object target;
    public ComputerInvocationHandler(Object object) {
        this.target = object;
    }


    /**
     * 指定代理对象的代理行为
     * @param proxy
     * @param method 目标对象的当前执行的方法
     * @param args 调用方法带的参数
     * @return
             * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            System.out.println("加价200");
            Object result = method.invoke(target, args);
            //。。。。
            return result;
        } catch (Exception ex) {
            //....

        } finally {
            //。。。
        }
        return null;
    }
}
