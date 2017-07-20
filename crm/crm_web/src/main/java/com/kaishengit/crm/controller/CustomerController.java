package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.util.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 客户管理控制器
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 我的客户首页
     */
    @GetMapping("/my")
    public String myCustomerHome(Model model,
                                 @RequestParam(required = false,defaultValue = "1",value = "p") Integer pageNum,
                                 @RequestParam(required = false,defaultValue = "") String keyword,
                                 HttpSession session) {
        Account account = (Account) session.getAttribute("curr_user");

        Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("pageNum",pageNum);

        keyword = StringsUtil.isoToUtf8(keyword);
        queryParam.put("keyword", keyword);

        queryParam.put("accountId",account.getId());

        PageInfo<Customer> pageInfo = customerService.findMyCustomer(queryParam);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("keyword",keyword);
        return "customer/my_home";
    }

    /**
     * 新增客户
     */
    @GetMapping("/my/new")
    public String newMyCustomer(Model model) {
        model.addAttribute("tradeList",customerService.findAllTrade());
        model.addAttribute("sourceList",customerService.findAllSource());
        return "customer/new_mycustomer";
    }

    @PostMapping("/my/new")
    public String saveCustomer(Customer customer, RedirectAttributes redirectAttributes, HttpSession session) {
        Account account = (Account) session.getAttribute("curr_user");
        customerService.saveNewCustomer(customer,account);
        redirectAttributes.addFlashAttribute("message","添加客户成功");
        return "redirect:/customer/my";
    }

}
