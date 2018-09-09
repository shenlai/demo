package com.sl.springbootdemo.Events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
public class MyEventHandleTest {

    /**
     * 参数为Object类型时，所有事件都会监听到
     * 参数为指定类型事件时，该参数类型事件或者其子事件（子类）都可以接收到
     */
    @EventListener
    public void event(ApplicationEventTest event){

        //event.printMsg(null);
    }

}

