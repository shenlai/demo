

package com.sl.springbootdemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
@Controller
public class HomeController {

    @Autowired
    private Environment env;

    @GetMapping("/demo")
    public String demo(Model model) {


        System.out.print(env.getProperty("server.port",Integer.class));
        return "/templates/admins/index";
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
