package com.sl.osmdemo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long hotelId;

    private String hotelName;

    private Integer cityId;

    private String email;

}
