package com.kaishengit.service;

import com.kaishengit.pojo.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void findById() throws Exception {

        Customer customer = customerService.findById(1002);
        //Assert.assertEquals(customer.getCustName(),"李美丽");
        System.out.println(customer.getCustName());
        System.out.println(customer.getAddress());
    }

    @Test
    public void update() {
        Customer customer = customerService.findById(1002);
        customer.setCustName("李妹妹");

        customerService.save(customer);
    }

    @Test
    public void save() {
        Customer customer = new Customer();
        customer.setCustName("Gavin King");
        customer.setMobile("099-123123123");

        customerService.save(customer);
    }

    @Test
    public void findAll() {
        List<Customer> customerList = customerService.findAll();
        for(Customer customer : customerList) {
            System.out.println(customer.getId() + " : " + customer.getCustName() + " -> " + customer.getAddress());
        }
    }

    @Test
    public void deleteById() {
        customerService.deleteById(1018);
    }

    @Test
    public void findByProperty() {
        List<Customer> customerList = customerService.findByProperty("source","自动上门"); //customerService.findByProperty("id",1000);//customerService.findByProperty("source","自动上门"); //customerService.findByProperty("level","★★★");
        for(Customer customer : customerList) {
            System.out.println(customer.getId() + " : " + customer.getCustName() + " -> " + customer.getAddress());
        }
    }

}