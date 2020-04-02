package com.javalanguage;

import org.junit.Test;

import java.util.Arrays;

public class jdk8InterfaceTest {

    @Test
    public void test(){
        jdk8Interface.staticMethod();

        new InstanceClass().defaultMethod();
    }


    /**
     * Lambda表达式
     */
    @Test
    public void test2(){

        Arrays.asList("a","b","c").forEach(x->System.out.println(x));
        Arrays.asList("a","b","c").sort((x,y)->{
            int result = x.compareTo(y);
            return result;
        });

    }

}
