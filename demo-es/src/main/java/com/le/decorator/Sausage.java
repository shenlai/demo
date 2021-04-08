package com.le.decorator;

/**
 * @author shenlai
 * @Description TODO
 * @create 2021/2/1 11:13
 */
public class Sausage extends Decorator {
    Sausage(HandPancake handPancake){
        super(handPancake);
    }
    @Override
    public String offerHandPancake() {
        return super.offerHandPancake()+" 加香肠";
    }
    @Override
    public Integer calcCost() {
        return super.calcCost()+3;
    }
}