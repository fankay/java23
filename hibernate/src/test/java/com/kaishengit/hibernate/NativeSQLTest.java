package com.kaishengit.hibernate;

import com.kaishengit.pojo.Account;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class NativeSQLTest {

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
        String sql = "select id,user_name,address,age from account WHERE user_name = :name";

        SQLQuery sqlQuery = session.createSQLQuery(sql).addEntity(Account.class);
        //sqlQuery.setParameter(0,"王思雨");
        sqlQuery.setParameter("name","王思雨");

        sqlQuery.setFirstResult(2);
        sqlQuery.setMaxResults(5);

        List<Account> accountList = sqlQuery.list();

        for(Account account : accountList) {
            System.out.println(account);
        }

        /*List<Object[]> accountList = sqlQuery.list();
        for(Object[] array : accountList) {
            System.out.println(array[0]);
            System.out.println(array[1]);
            System.out.println(array[2]);
            System.out.println(array[3]);
            System.out.println("------------------------------");
        }*/
    }



}
