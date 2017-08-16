package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name","Jack");

        List<String> nameList = Arrays.asList("张三","李四");
        model.addAttribute("nameList",nameList);

        return "main";
    }

}
