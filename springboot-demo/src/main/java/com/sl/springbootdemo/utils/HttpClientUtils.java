package com.sl.springbootdemo.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * //https://blog.csdn.net/a491857321/article/details/78143993
 * //https://blog.csdn.net/minicto/article/details/56677420
 */
public class HttpClientUtils {

    /**
     * httpGet
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        CloseableHttpClient httpClient = getHttpClient(null);
        return executeGet(url, httpClient);
    }

    /**
     * httpGet:支持自定义重试策略
     *
     * @param url
     * @param retryStrategy
     * @return
     */
    public static String getRetry(String url, ServiceUnavailableRetryStrategy retryStrategy) {
        CloseableHttpClient httpClient = getHttpClient(retryStrategy);
        return executeGet(url, httpClient);
    }


    /**
     * httpPost
     *
     * @param url
     * @return
     */
    public static String post(String url, Object obj){
        CloseableHttpClient httpClient = getHttpClient(null);
        return executePost(url, obj, httpClient);
    }

    /**
     * httpPost:支持自定义重试策略
     *
     * @param url
     * @param retryStrategy
     * @return
     */
    public static String postRetry(String url, Object obj,ServiceUnavailableRetryStrategy retryStrategy){
        CloseableHttpClient httpClient = getHttpClient(null);
        return executePost(url, obj, httpClient);
    }

    private static CloseableHttpClient getHttpClient(ServiceUnavailableRetryStrategy retryStrategy) {
        if (retryStrategy != null) {
            return HttpClientBuilder.create().setServiceUnavailableRetryStrategy(retryStrategy).build();
        }
        return HttpClientBuilder.create().build();
    }

    private static String executePost(String url, Object obj, CloseableHttpClient httpClient) {
        // 创建Post方法实例
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = null;
        if (obj instanceof String) {
            entity = new StringEntity(obj.toString(), "utf-8");
        } else {
            String jsonStr = JSON.toJSONString(obj);
            entity = new StringEntity(jsonStr, "utf-8");//解决中文乱码问题
        }

        //  private static final String FORM_URLENCODED = "application/json; charset=utf-8";
        //    private static final String ACCEPT_JSON = "application/json";
        httpPost.setEntity(entity);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");

        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).setExpectContinueEnabled(false).build();
        httpPost.setConfig(requestConfig);

//        httpPost.addHeader("Content-type","application/json; charset=utf-8");
//        httpPost.setHeader("Accept", "application/json");

        String res = "";
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (entity != null) {
                res = EntityUtils.toString(responseEntity);
            }
        } catch (Exception e) {
            //logger.error(url, e);
            return res;
        } finally {
            closeResource(httpClient, response);
        }
        return res;
    }


    private static String executeGet(String url, CloseableHttpClient httpClient) {
        // 创建Get方法实例
        HttpGet httpGet = new HttpGet(url);
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).setExpectContinueEnabled(false).build();
        httpGet.setConfig(requestConfig);
        String res = "";
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                res = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            //logger.error(url, e);
            return res;
        } finally {
            closeResource(httpClient, response);
        }
        return res;
    }


    /**
     * 释放资源
     *
     * @param httpClient
     * @param response
     */
    private static void closeResource(CloseableHttpClient httpClient, CloseableHttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                //logger.error("释放response资源异常:", e);
                e.printStackTrace();
            }
        }

        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (Exception e) {
                //logger.error("释放httpclient资源异常:", e);
                e.printStackTrace();
            }
        }
    }



}









