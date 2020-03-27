package com.sl.osmdemo.service.impl;

import com.sl.osmdemo.service.PhotoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotoServiceImplTest {

    @Resource
    private PhotoService photoService;

    @Test
    public void photoCompare() {

        //photoService.PhotoCompare(0L, 6000000L);
        photoService.PhotoCompare(30958L, 30959L);

        System.out.println("************执行完成***************");
    }


    @Test
    public void photoCompare2() {
        //photoService.PhotoCompare(0L, 6000000L);
        //photoService.PhotoCompare2(30958L, 30959L);
        photoService.PhotoCompare2(0L, 6000000L);

        System.out.println("************执行完成***************");
    }
}