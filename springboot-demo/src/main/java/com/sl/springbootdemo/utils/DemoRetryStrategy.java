package com.sl.springbootdemo.utils;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

/**
 * @Author: sl
 * @Date: 2018/12/21
 * @Description: TODO
 */
public class DemoRetryStrategy extends AbstractRetryStrategy {

    @Override
    public boolean isRetry(HttpResponse httpResponse, int executionCount, HttpContext httpContext) {
        if (httpResponse.getStatusLine().getStatusCode() != 200)
            return true;
        else
            return false;
    }

    @Override
    public void beforeRetryHandle() {
        //CtripConstant.refreshToken();
    }

    @Override
    public long getRetryInterval() {
        return 200;
    }

    @Override
    public int getMaxRetries() {
        return 3;
    }
}
