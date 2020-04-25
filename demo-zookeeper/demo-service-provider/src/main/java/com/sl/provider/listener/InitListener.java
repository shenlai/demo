package com.sl.provider.listener;

import com.sl.provider.zk.ServiceRegister;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.InetAddress;
import java.util.Properties;


public class InitListener implements ServletContextListener {

    @Value("${server.port}")
    private int port;

    /**
     * 容器初始化时调用
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
        try {
            //读取配置文件
//            Properties properties = new Properties();
//            properties.load(InitListener.class.getClassLoader().getResourceAsStream("application.properties"));
//            int port = Integer.valueOf(properties.getProperty("server.port"));
            //获取IP地址
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            ServiceRegister.register(hostAddress, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
