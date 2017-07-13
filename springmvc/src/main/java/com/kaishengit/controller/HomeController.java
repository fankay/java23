package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by fankay on 2017/7/13.
 */
@Controller
public class HomeController {

    //@RequestMapping("/hello")
    //@RequestMapping(value = "/hello",method = RequestMethod.GET) //以Get方式请求/hello
    @GetMapping("/hello")
    public String helloWorld() {
        System.out.println("Hello,SpringMVC");
        return "hello";
    }


}
