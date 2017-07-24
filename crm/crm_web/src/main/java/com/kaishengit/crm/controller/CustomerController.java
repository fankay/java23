package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.google.zxing.WriterException;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleChanceService;
import com.kaishengit.util.QrCodeUtil;
import com.kaishengit.util.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 客户管理控制器
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SaleChanceService saleChanceService;

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

        //查找客户关联的销售机会列表
        List<SaleChance> saleChanceList = saleChanceService.findSaleChanceByCustId(id);

        model.addAttribute("customer",customer);
        model.addAttribute("accountList",accountService.findAllAccount());
        model.addAttribute("chanceList",saleChanceList);
        return "customer/info";
    }

    /**
     * 显示客户二维码图片
     */
    @GetMapping("/my/qrcode/{id:\\d+}")
    public void showCustomerQRCode(@PathVariable Integer id,HttpServletResponse response) {
        Customer customer = customerService.findById(id);

        response.setContentType("image/png");

        //vcard 格式 https://zxing.appspot.com/generator
        StringBuffer str = new StringBuffer();
        str.append("BEGIN:VCARD\r\n");
        str.append("VERSION:3.0\r\n");
        str.append("N:").append(customer.getCustName()).append("\r\n");
        str.append("TITLE:").append(customer.getJobTitle()).append("\r\n");
        str.append("TEL:").append(customer.getMobile()).append("\r\n");
        str.append("ADR:").append(customer.getAddress()).append("\r\n");
        str.append("END:VCARD\r\n");

        try {
            OutputStream outputStream = response.getOutputStream();
            QrCodeUtil.writeToStream(str.toString(), outputStream, 300, 300);
            outputStream.flush();
            outputStream.close();
        } catch (IOException|WriterException ex) {
            throw new RuntimeException("渲染二维码失败",ex);
        }
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

    /**
     * 将客户放入公海
     */
    @GetMapping("/my/{id:\\d+}/share/public")
    public String shareCustomerToPublic(@PathVariable Integer id,HttpSession session,
                                        RedirectAttributes redirectAttributes) {
        Account account = getCurrUser(session);
        Customer customer = customerService.findById(id);
        if(customer == null) {
            throw new NotFoundException();
        }
        if(!customer.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        customerService.shareCustomerToPublic(customer,account);
        redirectAttributes.addFlashAttribute("message","成功将客户[ "+customer.getCustName()+" ]放入公海");
        return "redirect:/customer/my";
    }

    /**
     * 将客户转移给他人
     */
    @GetMapping("/my/{custId:\\d+}/tran/{accountId:\\d+}")
    public String tranCustomerToAccount(@PathVariable Integer custId,
                                        @PathVariable Integer accountId,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes) {
        Account account = getCurrUser(session);
        Customer customer = customerService.findById(custId);
        if(customer == null) {
            throw new NotFoundException();
        }
        if(!customer.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        customerService.transferCustomerToAccount(customer,accountId,account);

        redirectAttributes.addFlashAttribute("message","成功将客户[ "+customer.getCustName()+" ]转移");
        return "redirect:/customer/my";
    }


    /**
     * 将客户导出为excel
     */
    @GetMapping("/my/export")
    public void exportExcel(HttpServletResponse response,HttpSession session) throws Exception {
        Account account = getCurrUser(session);

        //告诉浏览器输出内容的MIME
        response.setContentType("application/vnd.ms-excel");
        //设置弹出对话框的文件名称
        response.addHeader("Content-Disposition"," attachment;filename=\"customer.xls\"");
        customerService.exportAccountCustomerToExcel(account,response.getOutputStream());
    }










}
