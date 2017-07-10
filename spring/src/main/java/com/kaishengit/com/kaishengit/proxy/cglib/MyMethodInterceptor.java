package com.kaishengit.com.kaishengit.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by fankay on 2017/7/10.
 */
public class MyMethodInterceptor implements MethodInterceptor {

    /**
     *
     * @param o 目标对象
     * @param method 目标对象的方法
     * @param objects 方法的参数
     * @param methodProxy 代理对象的方法
     * @return 方法执行后的返回值
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("闪光....");
        Object result = methodProxy.invokeSuper(o,objects); //调用父类中的方法
        System.out.println("闪光....闪光....闪光....");
        return result;
    }
}
