package com.sl.springlearning.aoptest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

/**
 * @Author: sl
 * @Date: 2019/3/19
 * @Description: TODO
 */

@Aspect
@Order(2)
public class AroundTwoClass {

    @Around("execution(* com.sl.springlearning.aoptest.SalaryManagerImpl.*(..))")
    public void aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("------AroundTwoClass begin----------");
        if (true) {
            System.out.println("------AroundTwoClass skip----------");
        } else {
            joinPoint.proceed();
        }
        System.out.println("------AroundTwoClass end----------");
    }
}
