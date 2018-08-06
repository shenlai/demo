package com.sl.springbootdemo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;


public class GBKCondition implements Condition {
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata){
        String encoding = context.getEnvironment().getProperty("file.encoding");

        if("gbk".equals(encoding.toLowerCase())){
            return  true;
        }
        return  false;
     }
}
