package com.kaishengit.hibernate;

import com.kaishengit.pojo.Account;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Assert;

import java.util.List;

public class Test {

    @org.junit.Test
    public void first() {
        //读取配置文件(从classpath中读取名称为hibernate.cfg.xml的配置文件)
        Configuration configuration = new Configuration().configure();
        //创建SessionFactory
        //SessionFactory sessionFactory = configuration.buildSessionFactory();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        //创建Session
        Session session = sessionFactory.getCurrentSession();
        //开启事务
        Transaction transaction = session.getTransaction();
        transaction.begin();

        Account account = new Account();
        account.setUsername("王思雨");
        account.setAddress("北京");
        account.setAge(23);

        session.save(account);//merge=saveOrUpdate 不改变对象状态

        //session.clear(); //将session关联的所有对象从session中清除

        account.setUsername("王老五");
        session.flush();


        //关闭事务（提交 | 回滚）
        transaction.commit();// session.close();

    }

    @org.junit.Test
    public void updateAccount() {

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Account account = (Account) session.get(Account.class,6); //持久态

        session.getTransaction().commit(); // 游离态

        Session session2 = HibernateUtil.getSession();
        session2.beginTransaction();

        account.setUsername("李老八"); //游离态
        session2.merge(account); // 再次 持久态

        session2.getTransaction().commit(); //再次 游离态


    }



    @org.junit.Test
    public void save() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Account account = new Account();
        account.setUsername("王思雨");
        account.setAddress("北京");
        account.setAge(23);

        session.persist(account);
        System.out.println(account.getId());


        session.getTransaction().commit();
    }


    @org.junit.Test
    public void findById() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Account account = (Account) session.get(Account.class,22);
        //System.out.println(account.getUsername());
        session.getTransaction().commit();

        Assert.assertNull(account);

    }

    @org.junit.Test
    public void findByIdWithLoad() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Account account = (Account) session.load(Account.class,22);
        //System.out.println(account.getUsername());

        System.out.println(account.getUsername());

        session.getTransaction().commit();



        //System.out.println(account.getUsername());
    }



    @org.junit.Test
    public void update() {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Account account = (Account) session.get(Account.class,1);
        account.setUsername("张三丰");

        session.getTransaction().commit();
    }

    @org.junit.Test
    public void delete() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Account account = (Account) session.get(Account.class,1);
        session.delete(account);


        session.getTransaction().commit();
    }

    @org.junit.Test
    public void findAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        String hql = "from Account where username = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0,"tom");

        List<Account> accountList = query.list();

        for(Account account : accountList) {
            System.out.println(account.getUsername() + " -> " + account.getAddress());
        }

        session.getTransaction().commit();
    }

}
