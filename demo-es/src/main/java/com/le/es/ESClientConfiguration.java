package com.le.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shenlai
 * @Description TODO
 * @create 2020/9/27 10:54
 */
@Slf4j
@Configuration
public class ESClientConfiguration {
    @Autowired
    private ESResourceConfigValue resourceConfigValue;

    @Bean("resourceRestHighLevelClient")
    public RestHighLevelClient resourceRestHighLevelClient() {

        log.info("es config: {}", resourceConfigValue);

        return EsConnectionConfigBuilder.build(resourceConfigValue.getPort(), resourceConfigValue.getAddr(), resourceConfigValue.getUsername(), resourceConfigValue.getPassword())
                .buildEsClient();
    }


//    @Bean("resourceRestHighLevelClient")
//    public RestHighLevelClient restClient (){
//      return   new RestHighLevelClient(
//                RestClient.builder(new HttpHost(resourceConfigValue.getAddr(), resourceConfigValue.getPort()))
//                        // 连接失败处理
//                        .setFailureListener(new RestClient.FailureListener() {
//                            public void onFailure(HttpHost host) {
//                                log.error("init client error, host:{}", host);
//                            }
//                        })
//                        // 最大重试超时时间
//                        //.setMaxRetryTimeoutMillis(5000)
//                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() { // 认证
//                            @Override
//                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//                                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(resourceConfigValue.getUsername(), resourceConfigValue.getPassword()));
//                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//                            }
//                        })
//        );
//    }



}
