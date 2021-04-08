package com.le.decorator;

/**
 * @author shenlai
 * @Description TODO
 * @create 2021/2/1 11:11
 */
public interface HandPancake {
    /**
     * 提供手抓饼
     */
    String offerHandPancake();
    /**计算手抓饼的价格
     * @return
     */
    Integer calcCost();
}
