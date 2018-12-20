package com.sl.springbootdemo.EnableAnnotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//@Import(MyImportSelector.class)
//@Import(MyImportBeanDefinitionRegistrar.class)
public @interface EnableLog {
    String name();
}
