package com.sl.anno.cap5;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinCondition implements Condition {

    /**
     * ConditionContext:判断条件可以使用的上下文环境
     * *AnnotatedTypeMetadata: 注解的信息
     * @param conditionContext
     * @param annotatedTypeMetadata
     * @return
     */
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //获取当前IOC容器正在使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        //获取当前环境变量(包括我们操作系统是WIN还是LINUX??)
        Environment environment = conditionContext.getEnvironment();
        String os_name = environment.getProperty("os.name");
        if(os_name.contains("linux")){
            return true;
        }
        return false;
    }
}
