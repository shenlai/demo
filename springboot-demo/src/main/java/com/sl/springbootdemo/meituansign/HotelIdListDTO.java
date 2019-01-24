package com.sl.springbootdemo.meituansign;

import lombok.Data;

import java.util.List;

/**
 * @Author: sl
 * @Date: 2018/12/29
 * @Description: TODO
 */
@Data
public class HotelIdListDTO {

    private Long maxId;
    private List<Long> hotelIds;
}
