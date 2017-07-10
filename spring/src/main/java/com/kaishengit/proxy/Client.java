package com.kaishengit.proxy;

/**
 * Created by fankay on 2017/7/10.
 */
public class Client {

    public static void main(String[] args) {
        Lenovo lenovo = new Lenovo();
        Dell dell = new Dell();
        LenovoProxy proxy = new LenovoProxy(dell);
        proxy.sales();
    }

}
