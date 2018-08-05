package com.sl.springbootdemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;

@RestController
public class HomeController {

    @Autowired
    private Environment env;

    @RequestMapping("/demo")
    public String demo() {


        System.out.print(env.getProperty("server.port",Integer.class));
        return "Spring Boot Demo 修改";
    }


    @Value("${server.port}")
    private String port;

    @Value("${server.title:默认值}")
    private String title;


    @RequestMapping("/demo2")
    public String demo2() {
        System.out.print("端口号:"+port);
        System.out.print("title:"+title);
        return "Spring Boot Demo 修改";
    }

    @Autowired
    private PropertiesConfig config;
    @RequestMapping("/demo3")
    public String demo3() {
        System.out.print(config.getName());
        System.out.print(config.getPassword());
        return "Spring Boot Demo";
    }


}
