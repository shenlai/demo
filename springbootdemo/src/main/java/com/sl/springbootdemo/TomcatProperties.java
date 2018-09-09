package com.sl.springbootdemo;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tomcat")
public class TomcatProperties {
    private String port;

    private String host;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "TomcatProperties{" +
                "port=" + port +
                ", host=" + host +
                '}';
    }
}
