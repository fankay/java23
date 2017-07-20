package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.util.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 客户管理控制器
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

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
        Account account = getCurrUser(session);

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
        Account account = getCurrUser(session);
        customerService.saveNewCustomer(customer,account);
        redirectAttributes.addFlashAttribute("message","添加客户成功");
        return "redirect:/customer/my";
    }

    /**
     * 显示客户详情
     */
    @GetMapping("/my/{id:\\d+}")
    public String showCustomerInfo(@PathVariable Integer id,Model model,HttpSession session) {
        Account account = getCurrUser(session);

        //根据ID查找对应的客户
        Customer customer = customerService.findById(id);
        //判断customer对象是否存在，如果不存在，则404
        if(customer == null) {
            throw new NotFoundException();
        }
        //判断是否时当前用户的客户
        if(!customer.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }
        model.addAttribute("customer",customer);
        return "customer/info";
    }

    /**
     * 修改客户
     */
    @GetMapping("/my/{id:\\d+}/edit")
    public String editCustomer(@PathVariable Integer id,Model model,HttpSession session) {
        Account account = getCurrUser(session);
        //根据ID查找对应的客户
        Customer customer = customerService.findById(id);
        //判断customer对象是否存在，如果不存在，则404
        if(customer == null) {
            throw new NotFoundException();
        }
        //判断是否时当前用户的客户
        if(!customer.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        model.addAttribute("customer",customer);
        model.addAttribute("tradeList",customerService.findAllTrade());
        model.addAttribute("sourceList",customerService.findAllSource());
        return "customer/edit_mycustomer";
    }

    @PostMapping("/my/{id:\\d+}/edit")
    public String editCustomer(Customer customer,HttpSession session,RedirectAttributes redirectAttributes) {
        Account account = getCurrUser(session);
        if(!account.getId().equals(customer.getAccountId())) {
            throw new ForbiddenException();
        }
        customerService.editCustomer(customer);
        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/customer/my/"+customer.getId();
    }

    /**
     * 删除客户
     */
    @GetMapping("/my/{id:\\d+}/del")
    public String delCustomer(@PathVariable Integer id,HttpSession session) {
        Account account = getCurrUser(session);
        Customer customer = customerService.findById(id);
        if(customer == null) {
            throw new NotFoundException();
        }
        if(!customer.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        customerService.delCustomer(customer);
        return "redirect:/customer/my";
    }













}
