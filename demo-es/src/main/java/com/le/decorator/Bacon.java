package com.le.decorator;

/**
 * @author shenlai
 * @Description TODO
 * @create 2021/2/1 11:13
 */
public class Bacon extends Decorator {
    Bacon(HandPancake handPancake){
        super(handPancake);
    }

    @Override
    public String offerHandPancake() {
        return super.offerHandPancake()+" 加培根";
    }
    @Override
    public Integer calcCost() {
        return super.calcCost()+4;
    }
}
