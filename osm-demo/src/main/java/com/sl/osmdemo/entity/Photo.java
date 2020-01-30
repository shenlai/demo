package com.sl.osmdemo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: sl
 * @Date: 2020/1/16
 * @Description:
 */
@Data
public class Photo implements Serializable {
    private static final long serialVersionUID = -7620949287264184570L;

    private Long photoId;

    private String url;

    private String size;

    private Long roomId;

    private Long hotelId;


    private Integer status;
}
