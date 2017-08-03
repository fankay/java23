package com.kaishengit;

import com.kaishengit.pojo.Customer;

public class Son extends Father<String,Customer> {

    public Son() {
        super();
        System.out.println(this);
    }

}
