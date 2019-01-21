package com.sl.springbootdemo.eac;

import com.sl.springbootdemo.IdGenerate.snowflake.SnowflakeUidGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RunnableConfiguration {

    @Bean
    public Runnable createRunnable(){
        return  ()->{};
    }

    @Bean
    public SnowflakeUidGenerator customerUidGenerator() {
        long workerId = SnowflakeUidGenerator.getWorkerIdByIP(16);
        return new SnowflakeUidGenerator(workerId);
    }
}
