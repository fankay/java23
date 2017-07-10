package com.kaishengit.com.kaishengit.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created by fankay on 2017/7/10.
 */
public class Test {

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        //设置目标对象（父类）
        enhancer.setSuperclass(Mouse.class);
        //设置代理行为(MethodInterceptor接口的实现类)
        enhancer.setCallback(new MyMethodInterceptor());

        //通过父类执指向子类的形式获取目标对象的代理对象
        Mouse mouse = (Mouse) enhancer.create();
        mouse.move();











    }
}
