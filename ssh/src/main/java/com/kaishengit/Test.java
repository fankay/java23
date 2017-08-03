package com.kaishengit;

public class Test {

    public static void main(String[] args) {

        //如果父类对象是通过子类对象产生的，那么父类对象中的this关键字指的是子类对象
        Son son = new Son();



        /*System.out.println("----------------------");
        //如果父类对象是自己new出来的，那么父类对象中this关键字指的是父类对象(自己)
        Father father = new Father();*/
    }

}
