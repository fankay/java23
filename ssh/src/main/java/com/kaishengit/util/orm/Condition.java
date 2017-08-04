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
                // q_eq_s_userName_or_mobile = 123  (userName = 123 or mobile = 123)
                // q_like_s_custName
                try {
                    value = new String(value.getBytes("ISO8859-1"),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String[] array = key.split("_",4);

                String valueType = array[2];
                Object conditionValue = convertValueType(value,valueType);

                Condition condition = new Condition();
                condition.setPropertyName(array[3]); // userName_or_mobile
                condition.setValue(conditionValue);
                condition.setType(array[1]);

                conditionList.add(condition);

                request.setAttribute(key,value);
            }
        }

        return conditionList.toArray(new Condition[]{});
    }

    /**
     * 将value根据valueType进行类型转换
     * @param value
     * @param valueType
     * @return
     */
    private static Object convertValueType(String value, String valueType) {
        if("s".equalsIgnoreCase(valueType)) {
            return value;
        } else if("i".equalsIgnoreCase(valueType)) {
            return Integer.valueOf(value);
        } else if("d".equalsIgnoreCase(valueType)) {
            return Double.valueOf(value);
        } else if("f".equalsIgnoreCase(valueType)) {
            return Float.valueOf(value);
        } else if("b".equalsIgnoreCase(valueType)) {
            return Boolean.valueOf(value);
        } else if("l".equalsIgnoreCase(valueType))  {
            return Long.valueOf(value);
        }
        return null;
    }
}
