package com.kaishengit;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Father<T,E> {

    public Father() {
        //System.out.println("super:" + this);
        Class<?> clazz = this.getClass();//获取子类的class对象

        //clazz.getSuperclass();//获取父类的class  Father

        ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass(); //获取泛型父类的class Father<Customer>
        Type[] classes = type.getActualTypeArguments(); // 获取泛型参数数组
        Class<?> paramClass = (Class<?>) classes[0];
        System.out.println(paramClass);


    }

}
