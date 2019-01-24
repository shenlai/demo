package com.sl.springbootdemo.meituansign;

import lombok.Data;

/**
 * @Author: sl
 * @Date: 2018/12/29
 * @Description: TODO
 */
@Data
public class MeituanHttpRequest {

    /**
     * 请求业务参数
     */
    private String data;

    /**
     * 请求业务方法名
     */
    private String method;

    /**
     * 安全凭证公钥
     */
    private String accesskey;

    /**
     * 随机整数
     */
    private Integer nonce;

    /**
     * 合作商ID
     */
    private Integer partnerId;

    /**
     * 发起请求时的时间戳
     */
    private Long timestamp;

    /**
     * 分销开放平台API版本号 1.0
     */
    private String version;

    /**
     * 计算后签名值
     */
    private String signature;

    public MeituanHttpRequest() {

    }

    public MeituanHttpRequest(String method, String data) {
        this.method = method;
        this.data = data;
    }

}
