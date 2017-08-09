package com.kaishengit.piao.systemmanager.controller;

import com.kaishengit.piao.systemmanager.api.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/")
    public String login(String mobile, String password, RedirectAttributes redirectAttributes) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(mobile,new Md5Hash(password).toString());

        String errorMessage = "";
        try {
            subject.login(token);
        } catch (UnknownAccountException | IncorrectCredentialsException ex) {
            errorMessage = "账号或密码错误";
        } catch (LockedAccountException ex) {
            errorMessage = "该账号已被禁用";
        } catch (AuthenticationException ex) {
            errorMessage = "认证异常";
        }
        if(!"".equals(errorMessage)) {
            redirectAttributes.addFlashAttribute("message",errorMessage);
            return "redirect:/";
        }
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }


}
