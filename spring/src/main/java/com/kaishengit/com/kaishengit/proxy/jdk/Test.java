package com.kaishengit.com.kaishengit.proxy.jdk;

import com.kaishengit.com.kaishengit.proxy.Computer;
import com.kaishengit.com.kaishengit.proxy.Lenovo;

import java.lang.reflect.Proxy;

/**
 * Created by fankay on 2017/7/10.
 */
public class Test {
    public static void main(String[] args) {

        //目标对象
        Lenovo lenovo = new Lenovo();
        //Handler对象
        ComputerInvocationHandler invocationHandler = new ComputerInvocationHandler(lenovo);
        //接口指向(动态)代理对象
        Computer computer = (Computer) Proxy.newProxyInstance(lenovo.getClass().getClassLoader(),
                lenovo.getClass().getInterfaces(),invocationHandler);

        //调用代理对象的方法
        computer.sales();

    }
}
