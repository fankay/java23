package com.kaishengit.controller;

import com.kaishengit.pojo.Customer;
import com.kaishengit.service.CustomerService;
import com.kaishengit.util.orm.Condition;
import com.kaishengit.util.orm.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String home(Model model,
                       @RequestParam(name = "p",defaultValue = "1")Integer pageNum, HttpServletRequest request) throws UnsupportedEncodingException {
        //List<Customer> customerList = customerService.findAll();
        //model.addAttribute("customerList",customerList);

        /*List<Condition> conditionList = new ArrayList<>();
        if(StringUtils.isNotEmpty(custName)) {
            custName = new String(custName.getBytes("ISO8859-1"),"UTF-8");
            conditionList.add(new Condition("custName",custName,"like"));
        }
        if(StringUtils.isNotEmpty(source)) {
            source = new String(source.getBytes("ISO8859-1"),"UTF-8");
            conditionList.add(new Condition("source",source,"eq"));
        }*/
        Condition[] conditionList = Condition.builderConditionList(request);
        Page<Customer> page = customerService.findByPageNum(pageNum,conditionList);
        model.addAttribute("page",page);
        return "customer/list";
    }

}
