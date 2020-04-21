package com.sl.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ProviderStater {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"classpath:dubbo-provider.xml"});

        context.start();

        System.out.println("start ...");
        //按任意键退出
        System.in.read();

    }


}
