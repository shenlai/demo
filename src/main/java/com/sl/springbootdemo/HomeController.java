package com.sl.springbootdemo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @RequestMapping("/demo")
    public String demo() {
        EnvTestConfig config = new EnvTestConfig();
        System.out.print(config.getServerPort());
        return "Spring Boot Demo 修改";
    }

}
