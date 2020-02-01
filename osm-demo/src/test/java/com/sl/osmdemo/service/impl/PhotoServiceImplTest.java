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
        photoService.PhotoCompare(1414407L, 1414409L);

        System.out.println("************执行完成***************");
    }
}