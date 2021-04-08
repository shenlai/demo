package com.le.decorator;

/**
 * @author shenlai
 * @Description TODO
 * @create 2021/2/1 11:12
 */
public class NotelessHandPancake implements HandPancake {
    /**
     * 提供noteless 家的手抓饼一份
     */
    @Override
    public String offerHandPancake() {
        return " noteless 家的手抓饼";
    }

    /**
     * 计算 noteless 家 一份手抓饼的价格
     *
     * @return
     */
    @Override
    public Integer calcCost() {
        return 3;
    }
}