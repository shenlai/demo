package com.sl.springlearning.aop;


/**
 * CGLIB 代理
 */
public class MathCaculatorV2 implements ICaculator {


    public int div(int i, int j) {
        System.out.println("MathCaculatorV2...div...");
        return i / j;
    }
}
