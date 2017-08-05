package com.kaishengit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext*.xml");
        context.start();
        System.out.println("服务提供者已启动.....");
        System.in.read();
    }

}
