package com.sl.luban.aop.annoations;

import java.lang.annotation.Annotation;

public class Test {

    public static void main(String[] args) {

        CityModel cityModel = new CityModel();

        Class clazz = cityModel.getClass();

        Annotation annotation = clazz.getAnnotation(Entity.class);

        if (annotation instanceof Entity) {
            System.out.println("获取到注解");
            System.out.println(((Entity) annotation).value());
        }

    }

}
