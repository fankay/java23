package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by fankay on 2017/7/19.
 */
@Controller
public class LoginController {

    @Autowired
    private AccountService accountService;

    /**
     * 去登录页面
     * @return
     */
    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping
    @ResponseBody
    public AjaxResult login(String mobile, String password, HttpSession httpSession) {
        try {
            Account account = accountService.login(mobile, password);
            httpSession.setAttribute("curr_user",account);

            return AjaxResult.success();
        } catch (AuthenticationException ex) {
            return AjaxResult.error(ex.getMessage());
        }
    }

    /**
     * 安全退出
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message","你已安全退出系统");
        return "redirect:/";
    }

    /**
     * 个人设置
     */
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

}
