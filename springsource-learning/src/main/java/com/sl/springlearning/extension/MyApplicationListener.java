package com.sl.springlearning.extension;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


//监听器实现ApplicationListener接口，或者方法上使用 @EventListener注解
@Component
public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {
    @Override
    public void onApplicationEvent(MyApplicationEvent event) {
        System.out.println("监听到事件：" + event);
    }

    /*
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
            System.out.println("监听到事件："+event);
    }
*/
}
