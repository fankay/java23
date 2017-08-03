package com.kaishengit.util.orm;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Hibernate中BaseDao的条件对象
 */
public class Condition {

    /**
     * 属性的名称
     */
    private String propertyName;
    /**
     * 属性的值
     */
    private Object value;
    /**
     * 比较方式
     */
    private String type;

    public Condition() {
    }

    public Condition(String propertyName, Object value, String type) {
        this.propertyName = propertyName;
        this.value = value;
        this.type = type;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 根据查询条件自动产生Condition条件集合
     * @param request
     * @return
     */
    public static Condition[] builderConditionList(HttpServletRequest request) {
        List<Condition> conditionList = new ArrayList<>();

        //获取查询条件 key
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = request.getParameter(key);

            //只处理以q_开头并且值存在的键值对
            if(key.startsWith("q_") && StringUtils.isNotEmpty(value)) {
                //约定的方式
                // q_eq_userName=abc
                // q_like_custName=xyz
                try {
                    value = new String(value.getBytes("ISO8859-1"),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String[] array = key.split("_");

                Condition condition = new Condition();
                condition.setPropertyName(array[2]);
                condition.setValue(value);
                condition.setType(array[1]);

                conditionList.add(condition);

                request.setAttribute(key,value);
            }
        }

        return conditionList.toArray(new Condition[]{});
    }
}
