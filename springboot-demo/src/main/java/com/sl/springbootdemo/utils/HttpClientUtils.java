package com.sl.springbootdemo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.ws.WebServiceException;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * //https://blog.csdn.net/a491857321/article/details/78143993
 * //https://blog.csdn.net/minicto/article/details/56677420
 */
public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);


    private static CloseableHttpClient getHttpClient(ServiceUnavailableRetryStrategy retryStrategy) {
        if (retryStrategy != null) {
            return HttpClientBuilder.create().setServiceUnavailableRetryStrategy(retryStrategy).build();
        }
        return HttpClientBuilder.create().build();
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
     * httpGet
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        CloseableHttpClient httpClient = getHttpClient(null);
        return executeGet(url, httpClient);
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
            logger.error(url, e);
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
                logger.error("释放response资源异常:", e);
                e.printStackTrace();
            }
        }

        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error("释放httpclient资源异常:", e);
                e.printStackTrace();
            }
        }
    }


    public static String postJson(String url, Object obj) throws Exception {

        // 创建HttpClient实例
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);

        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = null;
        if (obj instanceof String) {
            entity = new StringEntity(obj.toString(), "utf-8");
        } else {
            String jsonStr = JSON.toJSONString(obj);
            entity = new StringEntity(jsonStr, "utf-8");//解决中文乱码问题
        }
        httpPost.setEntity(entity);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            String resData = EntityUtils.toString(responseEntity);
            httpPost.abort();
            return resData;
        }
        return "";
    }

    public static String postXml(String url, Object obj) throws Exception {

        // 创建HttpClient实例
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);

        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = null;
        if (obj instanceof String) {
            entity = new StringEntity(obj.toString(), "utf-8");
        } else {
            String jsonStr = JSON.toJSONString(obj);
            entity = new StringEntity(jsonStr, "utf-8");//解决中文乱码问题
        }
        httpPost.setEntity(entity);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            String resData = EntityUtils.toString(responseEntity);
            httpPost.abort();
            return resData;
        }
        return "";
    }

    /**
     * HTTP请求(为WebService定制)
     *
     * @param requestUrl
     * @param requestData
     * @return
     * @throws Exception
     */
    public static String postForWebService(String requestUrl, String requestData) throws WebServiceException {
        logger.info("SOAP Request Message:[" + requestData + "]");
        String responseData = null;
        try {
            responseData = httpRequest(requestUrl, requestData);
        } catch (WebServiceException e) {
            //-------重复请求-------------
//          logger.warn("SOAP request error, try again", e);
//try {
//responseData = httpRequest(requestUrl, requestData);
//} catch (WebServiceException ex) {
            logger.error("SOAP request error", e);
            throw e;
//}
        }
        logger.info("SOAP Response Message:[" + responseData + "]");
        return responseData;
    }

    private static String httpRequest(String requestUrl, String requestData) throws WebServiceException {
        BufferedReader in = null;
        PrintWriter out = null;
        StringBuffer result = new StringBuffer();
        try {
            System.setProperty("https.protocols", "TLSv1.2");//将HTTPS的安全协议指定为TLS1.2
            URL url = new URL(requestUrl);
            URLConnection urlconn = url.openConnection();
            // 设置通用的请求属性
            urlconn.setRequestProperty("accept", "*/*");
            urlconn.setRequestProperty("connection", "Keep-Alive");
            urlconn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            urlconn.setConnectTimeout(60000);
            urlconn.setReadTimeout(60000);
            // 发送POST请求必须设置如下两行
            urlconn.setDoOutput(true);
            urlconn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(urlconn.getOutputStream());
            // 发送请求参数
            out.print(requestData);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            throw new WebServiceException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
            if (out != null) {
                out.close();
            }
        }
        return result.toString();
    }
}




