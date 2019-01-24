package com.sl.springbootdemo;

import com.alibaba.fastjson.JSON;
import com.sl.springbootdemo.meituansign.HotelIdListRequest;
import com.sl.springbootdemo.meituansign.HotelIdListResponse;
import com.sl.springbootdemo.meituansign.meituanclient.FenxiaoHotelClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author: sl
 * @Date: 2018/12/29
 * @Description: TODO
 */
@Slf4j
public class FenxiaoHotelClientTest {

    @Test
    public void getTest() throws IOException {

        FenxiaoHotelClient client = new FenxiaoHotelClient(122, "xxx", "kkk");

        HotelIdListRequest request = new HotelIdListRequest();
        request.setMaxId(0L);
        request.setPageSize(10);

        HotelIdListResponse response = client.getPoiList(request);
        log.info(JSON.toJSONString(response));

        Assert.assertTrue(true);
    }

}
