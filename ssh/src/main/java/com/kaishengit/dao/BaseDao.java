package com.kaishengit.dao;

import com.kaishengit.util.orm.Condition;
import com.kaishengit.util.orm.Page;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
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

    public Long count(Condition... conditions) {
        Criteria criteria = getSession().createCriteria(clazz);
        for(Condition condition : conditions) {
            criteria.add(builderCriterionWithCondition(condition));
        }
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
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


    public Page<T> findByPageNum(Integer pageNum,Integer pageSize,String orderPropertyName,String orderType,Condition... conditions) {
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

        if("desc".equalsIgnoreCase(orderType)) {
            criteria.addOrder(Order.desc(orderPropertyName));
        }

        List<T> result = criteria.list();

        page.setItems(result);

        return page;
    }





    /**
     * 将condition对象转换为Criterion对象
     * @param condition
     * @return
     */
    private Criterion builderCriterionWithCondition(Condition condition) {
        if(condition.getType().equalsIgnoreCase("eq")) {
            return Restrictions.eq(condition.getPropertyName(),condition.getValue());
        } else if(condition.getType().equalsIgnoreCase("lt")) {
            return Restrictions.lt(condition.getPropertyName(),condition.getValue());
        } else if(condition.getType().equalsIgnoreCase("gt")) {
            return Restrictions.gt(condition.getPropertyName(),condition.getValue());
        } else if(condition.getType().equalsIgnoreCase("le")) {
            return Restrictions.le(condition.getPropertyName(),condition.getValue());
        } else if(condition.getType().equalsIgnoreCase("ge")) {
            return Restrictions.ge(condition.getPropertyName(),condition.getValue());
        } else if(condition.getType().equalsIgnoreCase("like")) {
            return Restrictions.like(condition.getPropertyName(),condition.getValue().toString(), MatchMode.ANYWHERE);
        }
        return null;
    }


}
