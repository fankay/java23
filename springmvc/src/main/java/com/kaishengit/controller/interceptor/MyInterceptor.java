package com.kaishengit.controller.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义SpringMVC拦截器
 * Created by fankay on 2017/7/13.
 */
public class MyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        System.out.println("~~~~~~我是拦截器~~~~~~");
        return true;
    }
}
