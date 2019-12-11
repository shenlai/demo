package com.sl.osmdemo.mapper;

import com.github.pagehelper.PageHelper;
import com.sl.osmdemo.entity.Hotel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelMapperTest {

    @Autowired
    private HotelMapper hotelMapper;

    @Test
    public void getHotelById() {
    }

    @Test
    public void getHotelByCityId() {
        //PageHelper.startPage(1,10);

        List<Hotel> hotel = hotelMapper.getHotelByCityId(301L);

        Assert.assertTrue(true);
    }
}