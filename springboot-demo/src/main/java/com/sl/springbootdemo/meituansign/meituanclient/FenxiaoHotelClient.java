package com.sl.springbootdemo.meituansign.meituanclient;

import com.alibaba.fastjson.JSON;
import com.sl.springbootdemo.meituansign.HotelIdListRequest;
import com.sl.springbootdemo.meituansign.HotelIdListResponse;
import com.sl.springbootdemo.meituansign.MeituanHttpRequest;

/**
 * @Author: sl
 * @Date: 2018/12/29
 * @Description: TODO
 */
public class FenxiaoHotelClient extends MeiTuanAbstractClient {
    public FenxiaoHotelClient(Integer partnerId, String accessKey, String secretKey){
        super(partnerId, accessKey, secretKey);
    }

    public HotelIdListResponse getPoiList(HotelIdListRequest body) {
        MeituanHttpRequest request = new MeituanHttpRequest("hotel.poi.list", JSON.toJSONString(body));
        String result = invokePost(request);
        return  JSON.parseObject(result,HotelIdListResponse.class);
    }
}

