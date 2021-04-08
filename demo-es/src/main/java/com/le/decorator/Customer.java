package com.le.decorator;

/**
 * @author shenlai
 * @Description TODO
 * @create 2021/2/1 11:14
 */
public class Customer {
    private String name;

    Customer(String name) {
        this.name = name;
    }

    public void buy(HandPancake handPancake) {
        System.out.println(name + "购买了 : " + handPancake.offerHandPancake() + " 一份, 花了 : " + handPancake.calcCost() + "块钱~");
        System.out.println();
    }
}