package com.lessmore.front.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product {

    private Long productId;

    private String productName;

    private String productCode;

    private String color;

    private Integer totalNum;

    private Integer soldNum;

    private String category;

    private BigDecimal costPrice;

    private BigDecimal salePrice;

    private String saleYear;

    private Long supplierId;

    private Date purchaseDate;

    private Date createTime;

    private String createUser;

    private String lastUpdateUser;

    private Integer status;



}
