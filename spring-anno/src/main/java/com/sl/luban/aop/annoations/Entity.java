package com.sl.luban.aop.annoations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//参考：com.annotations.hibernate_annotation.MyEntity

@Target(ElementType.TYPE)  //作用在类上
@Retention(RetentionPolicy.RUNTIME)   //注解作用域
public @interface Entity {
    String value();
}
