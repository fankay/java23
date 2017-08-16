package com.kaishengit.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Reference
    private UserService userService;

    @GetMapping("/user")
    public String home(Model model) {
        User user = userService.findById(18);
        model.addAttribute("user",user);
        return "home";
    }

}
