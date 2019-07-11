package com.tests;

import com.annotations.hibernate_annotation.MyColumn;
import com.annotations.hibernate_annotation.MyEntity;
import com.annotations.hibernate_annotation.MyGeneratedValue;
import com.annotations.hibernate_annotation.MyId;
import com.annotations.hibernate_annotation.MyTable;
import com.model.Hero;

import java.lang.reflect.Method;

/**
 * 解析注解
 * https://blog.csdn.net/qq1404510094/article/details/80577555
 */
public class ParseHibernateAnnotation {

    public static void main(String[] args) {
        Class<Hero> clazz = Hero.class;
        MyEntity myEntity = (MyEntity) clazz.getAnnotation(MyEntity.class);
        if (myEntity == null) {
            System.out.println("非实体类");
        } else {
            MyTable mytable = (MyTable) clazz.getAnnotation(MyTable.class);
            String tableName = mytable.name();
            System.out.println("当前实体类对应的表名为：" + tableName);
            Method[] methods = clazz.getMethods();
            Method primaryKeyMethod = null;
            for (Method m : methods) {
                MyId myId = m.getAnnotation(MyId.class);
                if (null != myId) {
                    primaryKeyMethod = m;
                    break;
                }
            }
            if (null != primaryKeyMethod) {
                System.out.println("找到主键：" + method2attribute(primaryKeyMethod.getName()));
                MyGeneratedValue myGeneratedValue = primaryKeyMethod.getAnnotation(MyGeneratedValue.class);
                System.out.println("主键增张策略：" + myGeneratedValue.strategy());
                MyColumn myColumn = primaryKeyMethod.getAnnotation(MyColumn.class);
                System.out.println("对应数据库字段为：" + myColumn.value());
            }

            System.out.println("其他非主键属性分别对应的数据库字段如下：");

            for(Method m:methods){
                if(m==primaryKeyMethod){
                    continue;
                }

                MyColumn myColumn = m.getAnnotation(MyColumn.class);
                if(myColumn==null){
                    continue; //setter方法没添加注解
                }
                String msg= String.format("属性：%s对应数据库字段为:%s",method2attribute(m.getName()),myColumn.value());
                System.out.println(msg);

            }


        }



    }

    private static String method2attribute(String methodName) {
        String result = methodName;
        ;
        result = result.replaceFirst("get", "");
        result = result.replaceFirst("is", "");
        if (result.length() <= 1) {
            return result.toLowerCase();
        } else {
            return result.substring(0, 1).toLowerCase() + result.substring(1, result.length());
        }

    }

}
