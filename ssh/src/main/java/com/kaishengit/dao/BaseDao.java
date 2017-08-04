package com.kaishengit.dao;

import com.kaishengit.pojo.Customer;
import com.kaishengit.util.orm.Condition;
import com.kaishengit.util.orm.Page;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.RootEntityResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class BaseDao<T,PK extends Serializable> {

    private Class<?> clazz;

    public BaseDao() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types =parameterizedType.getActualTypeArguments();
        clazz = (Class<?>) types[0];
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public T findById(PK id) {
        return (T) getSession().get(clazz,id);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    public void deleteById(PK id) {
        getSession().delete(findById(id));
    }

    public List<T> findAll() {
        Criteria criteria = getSession().createCriteria(clazz);
        return criteria.list();
    }

    public List<T> findByProperty(String propertyName,Object value) {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.add(Restrictions.eq(propertyName,value));
        return criteria.list();
    }

    public List<T> findByCondition(Condition condition) {
        Criteria criteria = getSession().createCriteria(clazz);
        //将condition对象转换为Criterion对象
        criteria.add(builderCriterionWithCondition(condition));
        return criteria.list();
    }

    public List<T> findByCondition(Condition... conditions) {
        Criteria criteria = getSession().createCriteria(clazz);
        for(Condition condition : conditions) {
            criteria.add(builderCriterionWithCondition(condition));
        }
        return criteria.list();
    }

    public Long count() {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    // findByPageNum(100);
    public Page<T> findByPageNum(Integer pageNum,Integer pageSize) {
        //总条数
        int totalSize = count().intValue();
        //总页数 = 总条数 / pageSize(+1)
        Page<T> page = new Page<T>(totalSize,pageSize,pageNum);

        Criteria criteria = getSession().createCriteria(clazz);
        criteria.setFirstResult(page.getStart());
        criteria.setMaxResults(pageSize);

        List<T> result = criteria.list();

        page.setItems(result);

        return page;
    }

    public Long count(Criteria criteria,Condition... conditions) {
        for(Condition condition : conditions) {
            criteria.add(builderCriterionWithCondition(condition));
        }

        ResultTransformer resultTransformer = criteria.ROOT_ENTITY;//获取之前查询的列

        criteria.setProjection(Projections.rowCount()); //select id,
        Long count =  (Long) criteria.uniqueResult();

        criteria.setProjection(null);
        criteria.setResultTransformer(resultTransformer);

        return count;
    }

    public Long count(Condition... conditions) {
        Criteria criteria = getSession().createCriteria(clazz);
        return count(criteria,conditions);
    }

    public Page<T> findByPageNum(Integer pageNum,Integer pageSize,Condition... conditions) {
        //总条数
        int totalSize = count(conditions).intValue();
        //总页数 = 总条数 / pageSize(+1)
        Page<T> page = new Page<T>(totalSize,pageSize,pageNum);

        Criteria criteria = getSession().createCriteria(clazz);

        for(Condition condition : conditions) {
            criteria.add(builderCriterionWithCondition(condition));
        }

        criteria.setFirstResult(page.getStart());
        criteria.setMaxResults(pageSize);

        List<T> result = criteria.list();

        page.setItems(result);

        return page;
    }

    public Page<T> findByPageNum(Criteria criteria, Integer pageNum, Integer pageSize, String orderPropertyName, String orderType, Condition[] conditions) {
        //总条数
        int totalSize = count(criteria,conditions).intValue();
        //总页数 = 总条数 / pageSize(+1)
        Page<T> page = new Page<T>(totalSize,pageSize,pageNum);

        for(Condition condition : conditions) {
            criteria.add(builderCriterionWithCondition(condition));
        }

        criteria.setFirstResult(page.getStart());
        criteria.setMaxResults(pageSize);

        if("desc".equalsIgnoreCase(orderType)) {
            criteria.addOrder(Order.desc(orderPropertyName));
        }

        List<T> result = criteria.list();

        page.setItems(result);

        return page;
    }

    public Page<T> findByPageNum(Integer pageNum,Integer pageSize,String orderPropertyName,String orderType,Condition... conditions) {
        Criteria criteria = getSession().createCriteria(clazz);
        return findByPageNum(criteria,pageNum,pageSize,orderPropertyName,orderType,conditions);
    }





    /**
     * 将condition对象转换为Criterion对象
     * @param condition
     * @return
     */
    private Criterion builderCriterionWithCondition(Condition condition) {
        String propertyName = condition.getPropertyName();
        Object value = condition.getValue();
        String type = condition.getType();

        if (propertyName.contains("_or_")) {
            String[] propertyNames = propertyName.split("_or_");

            Disjunction disjunction = Restrictions.disjunction();
            for (String name : propertyNames) {
                disjunction.add(builderCriterion(name,type,value));
            }

            return disjunction;
        } else {
            return builderCriterion(propertyName, type, value);
        }
    }

    private Criterion builderCriterion(String propertyName,String type,Object value) {
        if(type.equalsIgnoreCase("eq")) {
            return Restrictions.eq(propertyName,value);
        } else if(type.equalsIgnoreCase("lt")) {
            return Restrictions.lt(propertyName,value);
        } else if(type.equalsIgnoreCase("gt")) {
            return Restrictions.gt(propertyName,value);
        } else if(type.equalsIgnoreCase("le")) {
            return Restrictions.le(propertyName,value);
        } else if(type.equalsIgnoreCase("ge")) {
            return Restrictions.ge(propertyName,value);
        } else if(type.equalsIgnoreCase("like")) {
            return Restrictions.like(propertyName,value.toString(), MatchMode.ANYWHERE);
        }
        return null;
    }


}
