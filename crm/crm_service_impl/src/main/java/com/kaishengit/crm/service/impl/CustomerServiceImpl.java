package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.CustomerExample;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Value("#{'${customer.trade}'.split(',')}") //SpringEL
    private List<String> tradeList;
    @Value("#{'${customer.source}'.split(',')}")
    private List<String> sourceList;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 获取所有行业数据
     * @return
     */
    @Override
    public List<String> findAllTrade() {
        return tradeList;
    }

    /**
     * 获取所有客户来源数据
     * @return
     */
    @Override
    public List<String> findAllSource() {
        return sourceList;
    }

    /**
     * 新增客户
     * @param customer 客户对象
     * @param account 当前登录系统的用户
     */
    @Override
    public void saveNewCustomer(Customer customer, Account account) {
        customer.setAccountId(account.getId());
        customer.setCreateTime(new Date());
        customerMapper.insert(customer);
    }

    /**
     * 查询当前登录对象的客户
     * @param queryParam 查询条件集合，包括分页当前页码
     * @return
     */
    @Override
    public PageInfo<Customer> findMyCustomer(Map<String,Object> queryParam) {
        Integer pageNum = (Integer) queryParam.get("pageNum");
        PageHelper.startPage(pageNum,10);
        List<Customer> customerList = customerMapper.findByQueryParam(queryParam);
        return new PageInfo<>(customerList);
    }
}
