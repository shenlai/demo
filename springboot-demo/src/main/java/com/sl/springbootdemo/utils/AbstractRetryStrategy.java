package com.sl.springbootdemo.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;

/**
 * @Author: sl
 * @Date: 2018/12/21
 * @Description: TODO
 */
public abstract class AbstractRetryStrategy implements ServiceUnavailableRetryStrategy {

    @Override
    public boolean retryRequest(HttpResponse httpResponse, int i, HttpContext httpContext) {
        boolean isRetry = isRetry(httpResponse, i, httpContext);
        if (isRetry) {
            beforeRetryHandle();
        }
        if (i >= this.getMaxRetries()) {
            return false;
        }
        return isRetry;
    }

    public abstract boolean isRetry(HttpResponse httpResponse, int i, HttpContext httpContext);

    public abstract void beforeRetryHandle();

    public abstract int getMaxRetries();


}
