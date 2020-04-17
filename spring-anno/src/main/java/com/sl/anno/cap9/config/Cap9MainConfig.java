package com.sl.anno.cap9.config;

import com.sl.anno.cap9.dao.TestDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 小结:@Resource和Autowired的区别如下:
 * @Resource和Autowired一样可以装配bean
 * @Resource缺点: 不能支持@Primary功能
 *                不能支持@Autowired(required = false)的功能
 */
@Configuration
@ComponentScan({"com.sl.anno.cap9.controller", "com.sl.anno.cap9.service", "com.sl.anno.cap9.dao", "com.sl.anno.cap9.bean"})
public class Cap9MainConfig {
    //配置类中的bean 优先于 componentScan被加载
    //spring进行自装配的时候默认首选的bean
    @Primary //只要在这里申请Primary, 代表所有要注入TestDao的bean,
    @Bean("testDao2")
    //@Bean("testDao")  //两个beanName=testDao的 实例
    public TestDao testDao() {
        TestDao testDao = new TestDao();
        testDao.setFlag("2");
        return testDao;
    }
}
