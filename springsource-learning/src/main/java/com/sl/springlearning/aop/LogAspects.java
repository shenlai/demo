package com.sl.springlearning.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

//切面注解 @Aspect
@Aspect
public class LogAspects {

    //抽取公共的切入点表达式
    //1、本类引用
    //2、其他的切面引用
    @Pointcut("execution(public int com.sl.springlearning.aop.MathCaculator.*(..))")
    public void pointCut() {
    }

    ;

    //@Before在目标方法之前切入；切入点表达式（指定在哪个方法切入）
    @Before("pointCut()")
    public void logStart() {
        System.out.println("运行。。。@Before:参数列表是");
    }

    @After("pointCut()")
    public void logEnd() {
        System.out.println("结束。。。@After");
    }

    //JoinPoint一定要出现在参数表的第一位
    //returning="result"  指定result接收返回值
    //JoinPoint joinPoint 含有方法本身信息，方法名，参数值等
    //JoinPoint作为参数 则不必作为第一个参数
    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println("" + joinPoint.getSignature().getName() + "正常返回，" + "参数列表：" + Arrays.asList(joinPoint.getArgs()) + "。。@AfterReturning:运行结果：{" + result + "}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        System.out.println("" + joinPoint.getSignature().getName() + "异常。。。异常信息：{" + exception + "}");
    }

}
