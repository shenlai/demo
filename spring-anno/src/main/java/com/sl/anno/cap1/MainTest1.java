package com.sl.anno.cap1;

import com.sl.anno.cap1.config.MainConfig;
import com.sl.anno.cap1.model.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest1 {
    public static void main(String args[]) {

        //1:通过beans.xml的类加载到容器
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
//		//从容器中获取bean
//		Person person = (Person) applicationContext.getBean("person");

        //2:通过@Bean注解 注入
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = (Person) applicationContext.getBean("personXX");

        System.out.println(person);

        //打印bean name
        String[] namesForBean = applicationContext.getBeanNamesForType(Person.class);
        for (String name : namesForBean) {
            System.out.println(name);
        }
    }
}
