package com.sl.springbootdemo.utils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;

/**
 * @Author: sl
 * @Date: 2018/12/21
 * @Description: 重试策略
 */

public class UnavailableRetryStrategy implements ServiceUnavailableRetryStrategy {
    private final int maxRetries;
    private final long retryInterval;

    /**
     * @param maxRetries 重试次数
     * @param retryInterval 重试间隔
     */
    public UnavailableRetryStrategy(int maxRetries, int retryInterval) {
        this.maxRetries = maxRetries;
        this.retryInterval = (long)retryInterval;
    }




    @Override
    public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
        System.out.println("执行check判断");
        if (response.getStatusLine().getStatusCode() != 200 && executionCount < this.maxRetries)
            return true;
        else
            return false;

        //return executionCount <= this.maxRetries && response.getStatusLine().getStatusCode() == 503;
    }



    @Override
    public long getRetryInterval() {
        return this.retryInterval;
    }

}
