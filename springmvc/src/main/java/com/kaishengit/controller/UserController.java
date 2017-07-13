package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by fankay on 2017/7/13.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "user/list";
    }

    @GetMapping("/{id:\\d+}")
    public String show(@PathVariable Integer id) {
        System.out.println("ID:" + id);
        return "user/show";
    }

    @GetMapping("/{className:(java,web)\\d+}/{name}")
    public String showUser(@PathVariable String className,
                           @PathVariable String name) {
        System.out.println("ID:" + className);
        System.out.println("name:" + name);
        return "user/show";
    }

    @GetMapping("/save")
    public String save() {
        return "user/save";
    }

}
