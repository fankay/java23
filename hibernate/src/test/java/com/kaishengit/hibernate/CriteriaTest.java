package com.kaishengit.hibernate;

import com.kaishengit.pojo.Account;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CriteriaTest {

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
        //创建Criteria对象
        Criteria criteria = session.createCriteria(Account.class);

        List<Account> accountList = criteria.list();

        for(Account account : accountList) {
            System.out.println(account);
        }
    }

    @Test
    public void findByUserName() {
        Criteria criteria = session.createCriteria(Account.class);
        //where
        //criteria.add(Restrictions.eq("username","王思雨"));
        //criteria.add(Restrictions.eq("address","shanghai"));
        //criteria.add(Restrictions.lt("id",10));
        //criteria.add(Restrictions.in("id",new Integer[]{2,4,6,8}));
        //criteria.add(Restrictions.like("username","老"));
        //criteria.add(Restrictions.like("username","老", MatchMode.ANYWHERE));
        //criteria.add(Restrictions.or(Restrictions.eq("username","王老五"),Restrictions.eq("username","李老八")));

        //使用disjunction连接的查询条件，之间也是使用or连接   username = 王老五 or address = 北京
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.eq("userName","王老五"));
        disjunction.add(Restrictions.eq("address","北京"));

        criteria.add(disjunction);


        /*Account account = (Account) criteria.uniqueResult();
        System.out.println(account);*/

        List<Account> accountList = criteria.list();

        for(Account account : accountList) {
            System.out.println(account);
        }

    }

    @Test
    public void page() {
        Criteria criteria = session.createCriteria(Account.class);

        //分页参数
        criteria.setFirstResult(0);
        criteria.setMaxResults(5);

        //排序
        criteria.addOrder(Order.desc("address"));
        criteria.addOrder(Order.desc("id"));

        List<Account> accountList = criteria.list();

        for(Account account : accountList) {
            System.out.println(account);
        }
    }


    @Test
    public void count() {
        Criteria criteria = session.createCriteria(Account.class);

        //criteria.setProjection(Projections.count("age")); // count(age)
        //criteria.setProjection(Projections.rowCount()); // count(*)
        //criteria.setProjection(Projections.countDistinct("address")); //count(distinct address)

        //criteria.setProjection(Projections.max("id")); // max(id)

        //select count(*),max(id) from account
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.rowCount());
        projectionList.add(Projections.max("id"));

        criteria.setProjection(projectionList);

        Object[] array = (Object[]) criteria.uniqueResult();
        System.out.println(array[0] + " -> " + array[1]);

        /*Long count = (Long) criteria.uniqueResult();
        System.out.println(count);*/
    }














}
