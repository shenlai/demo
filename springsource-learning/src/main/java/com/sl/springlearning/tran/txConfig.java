package com.sl.springlearning.tran;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 声明式事务
 *
 *  1.导入相关依赖
 *   spring-jdbc
 *
 *  2.配置数据源  jdbcTemplate(Spring 提供的简化数据库操作的工具)操作数据
 *  3.添加Transactional注解，标注是一个事务方法
 *  4.EnableTransactionManagement开启基于注解的事务管理功能
 *  5.配置事务管理器来控制事务
 *  （SpringMvc中需要在xml中配置事务管理器）
 *
 *
 * 原理：
 * EnableTransactionManagement 通过TransactionManagementConfigurationSelector给容器中导入组件
 *   导入两个组件：
 *   AutoProxyRegistrar和 ProxyTransactionManagementConfiguration
 *    AutoProxyRegistrar：给容器中注册InfrastructureAdvisorAutoProxyCreator（是一个后置处理器）
 *       利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象
 *      （代理对象里面可以有增加器，代理对象执行方法利用拦截器链进行调用）
 *   ProxyTransactionManagementConfiguration作用？
 *      1. 给容器中注册事务增强其
 *          1）事务增强其主要用事务注解的信息 AnnotationTransactionAttributeSource解析事务
 *          2）事务拦截器  TransactionInterceptor：保存了事务的属性信息 ，事务管理器；
 *              他是一个MethodInteceptor:
 *              在目标方法执行的时候：
 *                  执行拦截器链，
 *                  事务拦截器
 *                      1） 先获取事务相关的属性
 *                      2）在获取PlatformTransactionManager，如果事先没有添加事务管理器transactionmanager
 *                        则默认从容器中取PlatformTransactionManager
 *                      3) 执行目标方法，
 *                         如果异常，获取到事务管理器，利用事务管理器回滚
 *                         如果正常，则利用事务管理器提交事务
 *
 *
 *
 *
 */
@EnableTransactionManagement
@ComponentScan("com.sl.springlearning.tran") //将dao 和 service 扫描到容器中
@Configuration
public class txConfig {

    @Bean
    public DataSource dataSource() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");

        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/bookstore");

        return  dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception {

        //spring 对Configuration类会特殊处理，dataSource()方法只是用来给容器中加组件的，
        // 多次调用只是从容器中寻找组件，并不是真正执行该方法
        return new JdbcTemplate(dataSource());
    }

    //事务管理器
    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        return  transactionManager;
    }

}
