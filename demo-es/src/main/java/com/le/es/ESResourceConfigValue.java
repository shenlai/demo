package com.le.es;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author shenlai
 * @Description TODO
 * @create 2020/9/27 10:50
 */
@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("config.esresource")
public class ESResourceConfigValue {
    int port;
    String addr, username, password;
}
