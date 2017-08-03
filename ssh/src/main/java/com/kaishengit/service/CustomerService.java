package com.kaishengit.service;

import com.kaishengit.dao.CustomerDao;
import com.kaishengit.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public Customer findById(Integer id) {
        return customerDao.findById(id);
    }

    public void save(Customer customer) {
        customerDao.save(customer);
    }

    public void delete(Customer customer) {
        customerDao.delete(customer);
    }

    public void deleteById(Integer id) {
        customerDao.deleteById(id);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public List<Customer> findByProperty(String propertyName,Object value) {
        return customerDao.findByProperty(propertyName,value);
    }



}
