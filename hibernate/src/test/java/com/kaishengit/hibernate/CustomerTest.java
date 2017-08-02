package com.kaishengit.hibernate;

import com.kaishengit.pojo.Customer;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Cache;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.junit.Test;


public class CustomerTest{// extends BaseTestCase {


    /*@Test
    public void save() {
        Customer customer = new Customer();
        customer.setName("李四");
        session.save(customer);
    }
*/

    @Test
    public void update() throws InterruptedException {

        String id = "40286d815da20c5c015da20c5da30000";
        Session session2 = HibernateUtil.getSession();
        session2.beginTransaction();
        Customer customer2 = (Customer) session2.get(Customer.class,id);
        customer2.setName("8888");
        session2.getTransaction().commit();
        System.out.println("session2 commit");

        /*final String id = "40286d815da20c5c015da20c5da30000";

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Customer customer = (Customer) session.get(Customer.class,id, LockOptions.UPGRADE); //以悲观锁查询
        customer.setName("1111");

        Thread thread = new Thread(new Runnable() {
            public void run() {
                Session session2 = HibernateUtil.getSession();
                session2.beginTransaction();
                Customer customer2 = (Customer) session2.get(Customer.class,id);
                customer2.setName("2222");
                session2.getTransaction().commit();
                System.out.println("session2 commit");
            }
        });

        thread.start();

        Thread.sleep(5000);

        session.getTransaction().commit();
        System.out.println("session1 commit");*/



        /*String id = "40286d815da20c5c015da20c5da30000";
        Customer customer = (Customer) session.get(Customer.class,id, LockOptions.UPGRADE); //以悲观锁查询
        customer.setName("Jack");
*/


       /* String id = "40286d815da20c5c015da20c5da30000";
        Customer customer = (Customer) session.get(Customer.class,id);

        Thread.sleep(15000);

        customer.setName("Jack");*/


    }


    /*@Test
    public void find() {
        String id = "40286d815da0efc9015da0efcb030000";
        Customer c1 = (Customer) session.get(Customer.class,id);

        boolean isCache = session.contains(c1);
        System.out.println(isCache);

        //session.clear();
        session.evict(c1);

        Customer c2 = (Customer) session.get(Customer.class,id);

        System.out.println(c2.getName());
    }*/

    /*@Test
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
*/
}
