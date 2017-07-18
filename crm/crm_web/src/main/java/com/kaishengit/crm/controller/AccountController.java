package com.kaishengit.crm.controller;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.service.DeptService;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTableResult;
import com.kaishengit.dto.ZTreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fankay on 2017/7/17.
 */
@Controller
@RequestMapping("/manage/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private DeptService deptService;

    @GetMapping
    public String accountList() {
        return "manage/accounts";
    }

    /**
     * DataTables加载数据
     * @return
     */
    @GetMapping("/load.json")
    @ResponseBody
    public DataTableResult<Account> loadAccountData(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String deptId = request.getParameter("deptId");
        Integer id = null;
        if(StringUtils.isNotEmpty(deptId)) {
            id = Integer.valueOf(deptId);
        }

        //获取Account的总记录数
        Long total = accountService.countAll();
        //获取Account过滤后的数量
        Long filtedTotal = accountService.countByDeptId(id);
        //当前页的记录
        List<Account> accountList = accountService.findByDeptId(id);

        return new DataTableResult<>(draw,total,filtedTotal,accountList);
    }



    /**
     * 加载ZTree树
     * @return
     */
    @PostMapping("/depts.json")
    @ResponseBody
    public List<ZTreeNode> loadDeptData() {
        List<Dept> deptList = deptService.findAllDept();

        List<ZTreeNode> nodeList = Lists.newArrayList(Collections2.transform(deptList, new Function<Dept,ZTreeNode>() {
            @Nullable
            @Override
            public ZTreeNode apply(@Nullable Dept dept) {
                ZTreeNode node = new ZTreeNode();
                node.setId(dept.getId());
                node.setName(dept.getDeptName());
                node.setpId(dept.getpId());
                return node;
            }
        }));
        return nodeList;
    }

    /**
     * 新建部门
     * @param dept
     * @return
     */
    @PostMapping("/dept/new")
    @ResponseBody
    public AjaxResult saveNewDept(Dept dept) {
        deptService.save(dept);
        return AjaxResult.success();
    }

    /**
     * 新建员工
     */
    @PostMapping("/new")
    @ResponseBody
    public AjaxResult saveNewAccount(Account account,Integer[] deptId) {
        accountService.saveAccount(account,deptId);
        return AjaxResult.success();
    }

    @PostMapping("/del/{id:\\d+}")
    @ResponseBody
    public AjaxResult delAccountById(@PathVariable Integer id) {
        accountService.delAccountById(id);
        return AjaxResult.success();
    }




}
