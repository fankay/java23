package com.kaishengit.crm.controller.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录验证拦截器
 * Created by fankay on 2017/7/19.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //除了 / 之外其他请求都要判断是否登录（session）
        String requestUrl = request.getRequestURI();

        //静态资源过滤
        if(requestUrl.startsWith("/static/")) {
            return true;
        }


        if("/".equals(requestUrl)) {
            return true;
        } else {
            HttpSession session = request.getSession();
            if(session.getAttribute("curr_user") == null) {
                response.sendRedirect("/");
            }
        }
        return true;
    }
}
