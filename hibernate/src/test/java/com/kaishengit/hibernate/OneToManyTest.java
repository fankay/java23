package com.kaishengit.hibernate;

import com.kaishengit.pojo.Address;
import com.kaishengit.pojo.User;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OneToManyTest {

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
    public void save() {
        //新建用户 新建地址  保存用户和地址   !!!!
        User user = new User();
        user.setUserName("Rose");

        Address address = new Address();
        address.setCityName("洛阳");
        address.setAddress("王城公园");
        address.setUser(user);


        session.save(user);
        session.save(address);
    }

    @Test
    public void save2() {
        //新建用户 新建地址  保存用户和地址
        User user = new User();
        user.setUserName("赵丽丽");

        Address address = new Address();
        address.setCityName("洛阳");
        address.setAddress("王城公园");
        address.setUser(user);

        Set<Address> addressSet = new HashSet<Address>();
        addressSet.add(address);
        user.setAddressSet(addressSet);

        session.save(user);
        session.save(address);
    }

    @Test
    public void deleteUser() {
        //删除用户 级联删除
        User user = (User) session.get(User.class,7);
        session.delete(user);
    }


    @Test
    public void saveNewAddress() {
        //给用户ID为1 的Alex增加收货地址 !!!!!
        User user = (User) session.get(User.class,1);

        Address address = new Address();
        address.setCityName("成都");
        address.setAddress("西南大道");
        address.setUser(user);

        session.save(address);
    }

    @Test
    public void findByAddressId() {
        //!!!!!
        Address address = (Address) session.get(Address.class,2);
        System.out.println(address.getCityName() + " ->" + address.getAddress());

        //预先加载延迟的对象
        Hibernate.initialize(address.getUser());

        //延迟加载，如果需要一，则再次发出请求进行查询
        //System.out.println(address.getUser().getUserName());
    }

    @Test
    public void findAddressByUserId() {
        //通过user_id外键查询某个用户拥有的地址 !!!!!!
        Criteria criteria = session.createCriteria(Address.class); //OGNL
        criteria.createAlias("user","u");

        //criteria.add(Restrictions.eq("user.id",2));
        criteria.add(Restrictions.eq("u.userName","韩晓丽"));

        List<Address> addressList = criteria.list();
        for(Address address : addressList) {
            System.out.println(address.getId() + "->" + address.getCityName() + " -> " + address.getAddress());
        }
    }

    @Test
    public void findByUserId() {
        //查询一个用户 加载用户对应的地址
        User user = (User) session.get(User.class,2);
        System.out.println(user.getUserName());

        //延迟加载
        Set<Address> addressSet = user.getAddressSet();
        for(Address address : addressSet) {
            System.out.println(address.getId() + "->" + address.getCityName() + " -> " + address.getAddress());
        }

    }

}
