package com.le.es;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

import java.util.concurrent.TimeUnit;

/**
 * @author shenlai
 * @Description TODO
 * @create 2020/11/20 16:02
 */
public class CustomConnectionKeepAliveStrategy extends DefaultConnectionKeepAliveStrategy {

    public static final CustomConnectionKeepAliveStrategy INSTANCE = new CustomConnectionKeepAliveStrategy();

    private CustomConnectionKeepAliveStrategy() {
        super();
    }

    /**
     * 最大keep alive的时间（分钟）
     * 这里默认为10分钟，可以根据实际情况设置。可以观察客户端机器状态为TIME_WAIT的TCP连接数，如果太多，可以增大此值。
     */
    private final long MAX_KEEP_ALIVE_MINUTES =10 ;

    @Override
    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
        long keepAliveDuration = super.getKeepAliveDuration(response, context);
        // <0 为无限期keepalive
        // 将无限期替换成一个默认的时间
        if (keepAliveDuration < 0) {
            return TimeUnit.MINUTES.toMillis(MAX_KEEP_ALIVE_MINUTES);
        }
        return keepAliveDuration;
    }
}
