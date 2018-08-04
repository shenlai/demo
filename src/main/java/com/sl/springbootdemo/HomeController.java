package com.sl.springbootdemo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @RequestMapping("/demo")
    public String demo() {

        return "Spring Boot Demo 修改";
    }

}
