package com.sl.springbootdemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class EnvTestConfig  implements EnvironmentAware {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env=environment;
    }

    public int getServerPort(){
        return env.getProperty("server.port",Integer.class);
    }

}
