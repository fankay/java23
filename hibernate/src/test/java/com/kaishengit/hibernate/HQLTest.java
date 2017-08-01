package com.kaishengit.hibernate;

import com.kaishengit.pojo.Account;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class HQLTest {

    private Session session;

    @Before
    public void before() {
        session = HibernateUtil.getSession();
        session.beginTransaction();
    }

    @After
    public void after() {
        session.getTransaction().commit();
    }

    @Test
    public void findAll() {

        String hql = "from Account";
        Query query = session.createQuery(hql);
        List<Account> accountList = query.list();

        for(Account account : accountList) {
            System.out.println(account.getId() + " -> " + account.getUserName() + " -> " + account.getAddress());
        }
    }

    @Test
    public void findByUserName() {
        String hql = "from Account as ac where ac.userName = :name";

        Query query = session.createQuery(hql);

        //query.setString(0,"王思雨");
        //query.setParameter(0,"王思雨");
        query.setParameter("name","王思雨");

        List<Account> accountList = query.list();

        for(Account account : accountList) {
            System.out.println(account.getId() + " -> " + account.getUserName() + " -> " + account.getAddress());
        }
    }


    @Test
    public void findBy() {
        String hql = "select id,userName,address from Account where age = :age";
        Query query = session.createQuery(hql);
        query.setParameter("age",23);

        List<Object[]> accountList = query.list();

        for(Object[] array : accountList) {
            System.out.println(array[0] + " -> " + array[1] + " -> " + array[2]);
        }
    }

    @Test
    public void count() {

        String hql = "select count(*),max(id) from Account";
        Query query = session.createQuery(hql);

        Object[] objects = (Object[]) query.uniqueResult();
        System.out.println("count:" + objects[0]);
        System.out.println("max:" + objects[1]);

       /* Long count = (Long) query.uniqueResult();
        System.out.println("count:" + count);*/

       /* List<Long> count = query.list();
        System.out.println(count.get(0));*/
    }


    @Test
    public void findByUserNameDesc() {
        String hql = "from Account as ac where ac.userName = :name order by id desc";

        Query query = session.createQuery(hql);

        //query.setString(0,"王思雨");
        //query.setParameter(0,"王思雨");
        query.setParameter("name","王思雨");

        List<Account> accountList = query.list();

        for(Account account : accountList) {
            System.out.println(account.getId() + " -> " + account.getUserName() + " -> " + account.getAddress());
        }
    }

    @Test
    public void findByUserNameDescPage() {
        String hql = "from Account as ac where ac.userName = :name order by id desc";

        Query query = session.createQuery(hql);

        //query.setString(0,"王思雨");
        //query.setParameter(0,"王思雨");
        query.setParameter("name","王思雨");

        //limit 4,4  分页
        query.setFirstResult(4);
        query.setMaxResults(4);

        List<Account> accountList = query.list();

        for(Account account : accountList) {
            System.out.println(account.getId() + " -> " + account.getUserName() + " -> " + account.getAddress());
        }
    }




}
