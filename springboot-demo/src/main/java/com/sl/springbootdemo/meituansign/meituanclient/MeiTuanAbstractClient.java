package com.sl.springbootdemo.meituansign.meituanclient;

import com.sl.springbootdemo.meituansign.MeituanHttpRequest;
import com.sl.springbootdemo.utils.HttpClientUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @Author: sl
 * @Date: 2018/12/29
 * @Description: TODO
 */

public class MeiTuanAbstractClient {

    private static final Logger log = LoggerFactory.getLogger(MeiTuanAbstractClient.class);

    private static final String FORM_URLENCODED = "application/json; charset=utf-8";
    private static final String ACCEPT_JSON = "application/json";

    private static final String VERSION = "1.0";
    private static final Random random = new Random();
    private Integer partnerId;  // 分销商业务id
    private String accessKey;
    private String secretKey;

    /**
     * 生产环境
     */
    protected String host = "http://fenxiao.meituan.com/opdtor/api";

    public MeiTuanAbstractClient(Integer partnerId, String accessKey, String secretKey) {
        if (StringUtils.isBlank(accessKey) || StringUtils.isBlank(secretKey)) {
            throw new IllegalArgumentException("Access Key or Secret Key is empty!");
        }
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.partnerId = partnerId;
    }

    private void setBaseParam(MeituanHttpRequest request) {
        request.setVersion(VERSION);
        request.setTimestamp(System.currentTimeMillis() / 1000);
        request.setPartnerId(this.partnerId);
        request.setAccesskey(this.accessKey);
        request.setNonce(random.nextInt(Integer.MAX_VALUE));
        request.setSignature(calculateSignature(request));
    }

    private String calculateSignature(MeituanHttpRequest request) {
        // 计算签名
        String rawSignStr = "accesskey=" + request.getAccesskey() + "&data=" + request.getData()
                + "&method=" + request.getMethod() + "&nonce=" + request.getNonce()
                + "&partnerId=" + request.getPartnerId() + "&timestamp=" + request.getTimestamp()
                + "&version=" + request.getVersion();
        return this.hmacSha1(rawSignStr, secretKey);
    }

    protected String invokePost(MeituanHttpRequest request) {
        setBaseParam(request);
        // 计算签名
        String responseStr = null;
        try {
            //responseStr = HttpClientUtil.invokePost(host, JsonUtil.object2Json(request), FORM_URLENCODED, ACCEPT_JSON);
            responseStr = HttpClientUtils.post(host, request);
            log.info("responseStr={}", responseStr);
        } catch (Exception e) {
            log.error("client call failed", e);
            throw e;
        }
        return responseStr;
    }

    private String hmacSha1(String plainText, String secretKey) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(secretKey), "HmacSHA1");
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return Base64.encodeBase64String(mac.doFinal(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(plainText)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
