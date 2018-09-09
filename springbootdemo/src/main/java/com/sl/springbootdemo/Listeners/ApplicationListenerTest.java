package com.sl.springbootdemo.Listeners;

import com.sl.springbootdemo.Events.ApplicationEventTest;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


//自定义事件监听器
//@Component
public class ApplicationListenerTest implements ApplicationListener<ApplicationEventTest> {

    @Override
    public void onApplicationEvent(ApplicationEventTest event) {

        event.printMsg(null);
    }
}
