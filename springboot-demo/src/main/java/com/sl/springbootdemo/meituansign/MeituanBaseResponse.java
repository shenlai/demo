package com.sl.springbootdemo.meituansign;

import lombok.Data;

/**
 * @Author: sl
 * @Date: 2018/12/29
 * @Description: 美团响应基类
 */
@Data
public class MeituanBaseResponse<T> {
    private Integer code;
    private String message;
    private Integer partnerId;
    private T result;

}
