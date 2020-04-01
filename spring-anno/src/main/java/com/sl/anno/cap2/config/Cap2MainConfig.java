package com.sl.anno.cap2.config;

import com.sl.anno.cap1.model.Person;
import com.sl.anno.cap2.controller.OrderController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;


@Configuration
//自定义Filter  只扫描对应包下带有@Controller注解的组件 useDefaultFilters = false
//@ComponentScan(value = "com.sl.anno.cap2", includeFilters = {
//        @Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
//}, useDefaultFilters = false)

//自定义Filter  排除扫描对应包下带有@Controller注解的组件,useDefaultFilters = true
//@ComponentScan(value = "com.sl.anno.cap2", excludeFilters = {
//        @Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
//}, useDefaultFilters = true)

//@ComponentScan(value = "com.sl.anno.cap2", includeFilters = {
//        @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {OrderController.class})
//}, useDefaultFilters = false)

//自定义Filter
@ComponentScan(value = "com.sl.anno.cap2", includeFilters = {
        @Filter(type = FilterType.CUSTOM, classes = {JamesTypeFilter.class})
}, useDefaultFilters = false)
//@ComponentScan(value = "com.sl.anno.cap2")
public class Cap2MainConfig {
    //给容器中注册一个bean, 类型为返回值的类型,
    @Bean
    public Person person01() {
        return new Person("james", 20);
    }
}
