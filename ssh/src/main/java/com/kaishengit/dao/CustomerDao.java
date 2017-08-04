package com.kaishengit.dao;

import com.kaishengit.pojo.Customer;

import com.kaishengit.util.orm.Condition;
import com.kaishengit.util.orm.Page;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao extends BaseDao<Customer,Integer> {

    public Page<Customer> findByPageNum(Integer pageNum, Integer pageSize, String orderPropertyName, String orderType, Condition... conditions) {
        Criteria criteria = getSession().createCriteria(Customer.class);
        criteria.createAlias("account","a");
        return findByPageNum(criteria,pageNum,pageSize,orderPropertyName,orderType,conditions);
    }



}
