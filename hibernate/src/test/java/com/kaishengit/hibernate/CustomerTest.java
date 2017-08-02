package com.kaishengit.hibernate;

import com.kaishengit.pojo.Customer;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.junit.Test;


public class CustomerTest {//extends BaseTestCase {


  /*  @Test
    public void save() {
        Customer customer = new Customer();
        customer.setName("张三");
        session.save(customer);
    }

    @Test
    public void find() {
        String id = "40286d815da0efc9015da0efcb030000";
        Customer c1 = (Customer) session.get(Customer.class,id);

        *//*boolean isCache = session.contains(c1);
        System.out.println(isCache);*//*

        //session.clear();
        session.evict(c1);

        Customer c2 = (Customer) session.get(Customer.class,id);

        System.out.println(c2.getName());
    }*/

    @Test
    public void find2() {

        //System.out.println(System.getProperty("java.io.tmpdir"));

        String id = "40286d815da0efc9015da0efcb030000";

        Session s1 = HibernateUtil.getSession();

        s1.beginTransaction();
        Customer c1 = (Customer) s1.get(Customer.class,id);

        s1.getTransaction().commit();


        Cache cache = HibernateUtil.getSessionFactory().getCache();
        //cache.evictEntityRegion(Customer.class);
        cache.evictEntity(Customer.class,"40286d815da0efc9015da0efcb030000");

        Session s2 = HibernateUtil.getSession();
        s2.beginTransaction();
        Customer c2 = (Customer) s2.get(Customer.class,id);
        System.out.println(c2.getName());
        s2.getTransaction().commit();
    }

}
