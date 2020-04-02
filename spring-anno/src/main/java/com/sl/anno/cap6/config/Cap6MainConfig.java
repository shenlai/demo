package com.sl.anno.cap6.config;

import com.sl.anno.cap1.model.Person;
import com.sl.anno.cap6.bean.Cat;
import com.sl.anno.cap6.bean.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * @Import注解使用： 1：声明一个bean
 * 2：导入@Configuration注解的配置类
 * 3：导入ImportSelector的实现类
 * 4：导入ImportBeanDefinitionRegistrar的实现类
 *
 *
 * <p>
 * 解释：
 * 2:导入@Configuration配置类 在Spring boot 中配置都一般都是自动导入的，所以我们不需要使用@Import，但是如果如果你自动扫包路径为:com.spring.example.app,
 * 而你想导入的配置类在com.spring.example.config下，那么该配置类就需要使用@Import导入，尤其是第三方jar包的配置类都需要借助@Import来导入
 * 比如：AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap6MainConfig.class);
 * Cap6MainConfigV2配置类没有被扫描， 可以通过@Import导入
 * <p>
 * 3：ImportSelector接口返回一个class（全称），该class会被spring容器所托管起来
 * 如果只是用@Component注释ImportSelector的实现类JamesImportSelector 该类的selectImports方法是不会被调用的
 * 我们需要使用@import导入自动配置文件选择器
 * <p>
 * 4:ImportBeanDefinitionRegistrar 注册器主要是在Spring boot 启动阶段动态的向容器中注册Bean Definition像Mybatis中的Mapper
 * 就是通过实现BeanDefinitionRegistrar接口来自动注册到容器中的(MapperScannerRegistrar )
 */

//测试@Import
@Configuration
//@Import(value = {Dog.class, Cat.class, JamesImportSelector.class, JamesImportBeanDefinitionRegistrar.class})
//@Import(value = {Dog.class, Cat.class})  //1 导入普通类  beanName为包全名+类名 com.sl.anno.cap6.bean.Dog 、com.sl.anno.cap6.bean.Cat
//@Import(value = {Cap6MainConfigV2.class})  //2 导入配置类
//@Import(value = {JamesImportSelector.class})
@Import(value = {Dog.class, Cat.class, JamesImportBeanDefinitionRegistrar.class})
public class Cap6MainConfig {
    /*
     * 给容器中注册组件的方式
     * 1：声明一个bean   @Bean: [导入第三方的类或包的组件],比如Person为第三方的类, 需要在我们的IOC容器中使用
     * 2：包扫描+组件的标注注解(@ComponentScan:  @Controller, @Service  @Reponsitory  @ Componet),一般是针对 我们自己写的类,使用这个
     *
     * 3,@Import:[快速给容器导入一个组件] 注意:@Bean有点简单
     *      3.1: @Import(要导入到容器中的组件):容器会自动注册这个组件,bean 的 id为全类名
     *      3.2: ImportSelector:是一个接口,返回需要导入到容器的组件的全类名数组
     *      3.3: ImportBeanDefinitionRegistrar:可以手动添加组件到IOC容器, 所有Bean的注册可以使用BeanDifinitionRegistry
     *           写JamesImportBeanDefinitionRegistrar实现ImportBeanDefinitionRegistrar接口即可
     *  4,使用Spring提供的FactoryBean(工厂bean)进行注册
     *
     *
     */
    //容器启动时初始化person的bean实例
    @Bean("person")
    public Person person() {
        return new Person("james", 20);
    }


//    @Bean
//    public JamesFactoryBean jamesFactoryBean() {
//        return new JamesFactoryBean();
//    }
}
