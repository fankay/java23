package com.kaishengit.piao.systemmanager.controller;

import com.kaishengit.piao.systemmanager.api.UserService;
import com.kaishengit.piao.systemmanager.modal.User;
import com.kaishengit.piao.systemmanager.shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
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
        Subject subject = ShiroUtil.getSubject();
        System.out.println("isAuthenticated? " + subject.isAuthenticated());
        System.out.println("isRemembered? " + subject.isRemembered());

        //如果是已认证的用户重新访问首页，则登出当前账号（认为用户是要切换账号）
        if(subject.isAuthenticated()) {
            subject.logout(); // 会自动删除rememberMe的cookie
        }
        //用户是被记住的
        if(!subject.isAuthenticated() && subject.isRemembered()) {
            Session session = subject.getSession();
            session.setAttribute("curr_user",ShiroUtil.getCurrentUser());
            return "redirect:/home";
        }
        return "index";
    }

    @PostMapping("/")
    public String login(String mobile, String password,boolean rememberMe, RedirectAttributes redirectAttributes) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(mobile,new Md5Hash(password).toString(),rememberMe);

        String errorMessage = "";
        try {
            subject.login(token);

          /*  if(subject.hasAllRoles(Arrays.asList("结算管理","库存管理"))) {

            }*/

            //获取当前登录的对象
            User user = ShiroUtil.getCurrentUser(); //(User) subject.getPrincipal();
            //将当前登录的用户放入session
            Session session = subject.getSession();
            session.setAttribute("curr_user",user);

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
