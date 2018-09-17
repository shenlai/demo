package com.sl.springlearning.extension;


import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //同监听器作用
    @EventListener(classes = {MyApplicationEvent.class})
    public  void listen(MyApplicationEvent event){

        System.out.println("UserService 监听到事件："+event.getMessage());

    }

}
