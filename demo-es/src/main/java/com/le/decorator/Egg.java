package com.le.decorator;

/**
 * @author shenlai
 * @Description TODO
 * @create 2021/2/1 11:13
 */
public class Egg extends Decorator {
    Egg(HandPancake handPancake){
        super(handPancake);
    }
    @Override
    public String offerHandPancake() {
        return super.offerHandPancake()+"加鸡蛋";
    }
    @Override
    public Integer calcCost() {
        return super.calcCost()+2;
    }
}