package com.sl.springbootdemo.extendsion;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Order(3)
@Component
public class ApplicationRunnerTest implements ApplicationRunner {

    //ApplicationArguments是对参数（main方法）做了进一步封装
    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("===MyApplicationRunner==="+ Arrays.asList(args.getSourceArgs()));
        System.out.println("===getOptionNames========"+args.getOptionNames());
        System.out.println("===getOptionValues======="+args.getOptionValues("name"));
        System.out.println("==getOptionValues========"+args.getOptionValues("password"));

    }
}
