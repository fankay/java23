package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 客户管理业务层
 */
public interface CustomerService {

    List<String> findAllTrade();
    List<String> findAllSource();

    void saveNewCustomer(Customer customer, Account account);

    PageInfo<Customer> findMyCustomer(Map<String,Object> queryParam);

    Customer findById(Integer id);

    void editCustomer(Customer customer);

    void delCustomer(Customer customer);

    void shareCustomerToPublic(Customer customer,Account account);

    void transferCustomerToAccount(Customer customer, Integer accountId,Account account);

    List<Customer> findByAccountId(Integer accountId);

    void exportAccountCustomerToExcel(Account account, OutputStream outputStream);

    List<Map<String,Object>> findCustomerLevelCount();

}
