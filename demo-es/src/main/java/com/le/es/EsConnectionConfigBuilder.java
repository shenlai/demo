package com.le.es;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.*;
import org.springframework.util.Assert;

/**
 * @author shenlai
 * @Description TODO
 * @create 2020/9/27 11:38
 */
@Slf4j
@Getter
@Setter
public class EsConnectionConfigBuilder {

    public static final RequestOptions ES_REQUEST_OPTION;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 地址
     */
    private String addr;

    /**
     * 账户
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    /**
     * 默认连接超时时间
     */
    public int connectTimeout = 1000;
    /**
     * 默认Socket 连接超时时间
     */
    public int socketTimeout = 2000;
    /**
     * 默认获取连接的超时时间
     */
    public int connectionRequestTimeout = 1000;
    /**
     * 默认最大路由连接数
     */
    public int maxConnPerRoute = 100;
    /**
     * 默认最大连接数
     */
    public int maxConnTotal = 100;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        // 默认缓存限制为100MB，此处修改为30MB。
        builder.setHttpAsyncResponseConsumerFactory(new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
        ES_REQUEST_OPTION = builder.build();
    }

    private EsConnectionConfigBuilder(Integer port, String addr, String username, String password) {
        this.port = port;
        this.addr = addr;
        this.username = username;
        this.password = password;
    }

    public static EsConnectionConfigBuilder build(Integer port, String address, String userName, String passWord) {
        return new EsConnectionConfigBuilder(port, address, userName, passWord);
    }

    /**
     * 注册
     *
     * @return
     */
    public RestHighLevelClient buildEsClient() {

        log.info("ES register start");
        Assert.notNull(getPort(), "port empty");
        Assert.notNull(getAddr(), "addr empty");
        Assert.notNull(getUsername(), "userName empty");
        Assert.notNull(getPassword(), "password empty");
        // 阿里云ES集群需要basic auth验证。
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //访问用户名和密码为您创建阿里云Elasticsearch实例时设置的用户名和密码，也是Kibana控制台的登录用户名和密码。
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(getUsername(), getPassword()));

        // 通过builder创建rest client，配置http client的HttpClientConfigCallback。
        // 单击所创建的Elasticsearch实例ID，在基本信息页面获取公网地址，即为ES集群地址。
        // RestHighLevelClient实例通过REST low-level client builder进行构造。
        RestClientBuilder builder = RestClient.builder(new HttpHost(getAddr(), getPort())).setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//            httpClientBuilder.setMaxConnTotal(getMaxConnTotal());
//            httpClientBuilder.setMaxConnPerRoute(getMaxConnPerRoute());
            return httpClientBuilder;
        });
//        .setRequestConfigCallback(requestConfigBuilder -> {
//            requestConfigBuilder.setConnectTimeout(getConnectTimeout());
//            requestConfigBuilder.setSocketTimeout(getSocketTimeout());
//            requestConfigBuilder.setConnectionRequestTimeout(getConnectionRequestTimeout());
//            return requestConfigBuilder;
//        });
        log.info("ES register success");
        return new RestHighLevelClient(builder);
    }

    public EsConnectionConfigBuilder setConnectionRequestTimeout(final int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        return this;
    }

    public EsConnectionConfigBuilder setConnectTimeout(final int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public EsConnectionConfigBuilder setSocketTimeout(final int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public EsConnectionConfigBuilder setMaxConnTotal(final int maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
        return this;
    }

    public EsConnectionConfigBuilder setMaxConnPerRoute(final int maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
        return this;
    }
}
