package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceRecord;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 销售机会控制器
 */
@Controller
@RequestMapping("/sales/")
public class SaleChanceController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;
    @Autowired
    private CustomerService customerService;

    /**
     * 我的销售机会首页
     * @return
     */
    @GetMapping("/my")
    public String home(Model model, HttpSession session,
                       @RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo) {
        Account account = getCurrUser(session);

        Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("pageNo",pageNo);
        queryParam.put("accountId",account.getId());

        PageInfo<SaleChance> pageInfo = saleChanceService.findByParam(queryParam);
        model.addAttribute("page",pageInfo);
        return "sales/my";
    }

    /**
     * 添加我的销售机会
     */
    @GetMapping("/my/new")
    public String newSaleChance(HttpSession session, Model model) {
        Account account = getCurrUser(session);
        //查找当前用户的所有客户
        List<Customer> customerList = customerService.findByAccountId(account.getId());
        //加载进度列表
        List<String> progressList = saleChanceService.findAllProgressList();

        model.addAttribute("customerList",customerList);
        model.addAttribute("progressList",progressList);
        return "sales/new_chance";
    }

    @PostMapping("/my/new")
    public String newSaleChance(SaleChance saleChance,RedirectAttributes redirectAttributes) {
        saleChanceService.saveNewChance(saleChance);

        redirectAttributes.addFlashAttribute("message","添加销售机会成功");
        return "redirect:/sales/my";
    }

    /**
     * 查看我的销售机会详情
     */
    @GetMapping("/my/{id:\\d+}")
    public String showSaleChance(@PathVariable Integer id,HttpSession session,Model model) {
        Account account = getCurrUser(session);
        SaleChance saleChance = saleChanceService.findSaleChanceById(id);
        if(saleChance == null) {
            throw new NotFoundException();
        }
        if(!saleChance.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        //获取该机会对应的跟进记录列表
        List<SaleChanceRecord> saleChanceRecordList = saleChanceService.findChanceRecordByChanceId(saleChance.getId());

        model.addAttribute("recordList",saleChanceRecordList);
        model.addAttribute("saleChance",saleChance);
        model.addAttribute("processList",saleChanceService.findAllProgressList());
        return "sales/chance";
    }

    /**
     * 删除销售机会
     */
    @GetMapping("/my/{id:\\d+}/del")
    public String delSaleChanceById(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        Account account = getCurrUser(session);
        SaleChance saleChance = saleChanceService.findSaleChanceById(id);
        if(saleChance == null) {
            throw new NotFoundException();
        }
        if(!saleChance.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        saleChanceService.delSaleChanceById(id);

        redirectAttributes.addFlashAttribute("message","删除销售机会成功");
        return "redirect:/sales/my";
    }

    /**
     * 给销售机会添加新的跟进记录
     */
    @PostMapping("/my/new/record")
    public String saveNewRecord(SaleChanceRecord record) {
        saleChanceService.saveNewChanceRecord(record);
        return "redirect:/sales/my/"+record.getSaleId();
    }

    /**
     * 更新销售机会的进度
     */
    @PostMapping("/my/{id:\\d+}/progress/update")
    public String updateSaleChanceProgress(Integer id,String progress) {
        saleChanceService.updateSaleChanceProgress(id,progress);
        return "redirect:/sales/my/"+id;
    }


}
