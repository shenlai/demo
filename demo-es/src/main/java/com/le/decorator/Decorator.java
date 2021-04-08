package com.le.decorator;

/**
 * @author shenlai
 * @Description TODO
 * @create 2021/2/1 11:12
 */
public abstract class Decorator implements HandPancake{
    private HandPancake handPancake;
    Decorator(HandPancake handPancake){
        this.handPancake = handPancake;
    }
    /**提供手抓饼
     * @return
     */
    @Override
    public String offerHandPancake() {
        return handPancake.offerHandPancake();
    }

    /**提供手抓饼的价格
     * @return
     */
    @Override
    public Integer calcCost() {
        return handPancake.calcCost();
    }
}