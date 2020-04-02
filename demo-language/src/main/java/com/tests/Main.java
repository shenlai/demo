package com.tests;

import com.annotations.SlAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AnnotationTest at = new AnnotationTest();
        at.execute();

        //获取Annotation 的class实例
        Class<AnnotationTest> c = AnnotationTest.class;

        //获取需要处理的方法实例
        Method method = c.getMethod("execute", new Class[]{});
        if (method.isAnnotationPresent(SlAnnotation.class)) {
            //获取该方法SlAnnotation注解实例
            SlAnnotation slAnnotation = method.getAnnotation(SlAnnotation.class);
            //执行该方法
            method.invoke(at, new Object[]{});
            //获取SlAnnotation属性值
            String source = slAnnotation.source();
            System.out.println("source:" + source);
        }

        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

    }

}

/**
 *
 * 元注解：
 *  元注解是可以注解到注解上的注解，或者说元注解是一种基本注解，但是它呢人够应用到其他的注解上面
 *  元注解标签 @Rentention @Documented @Target @Inherited @Repeatable 5种
 */
