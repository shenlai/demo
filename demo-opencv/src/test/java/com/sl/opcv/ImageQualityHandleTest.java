package com.sl.opcv;

import com.sl.opcv.imgQuality.ImageQuality;
import com.sl.opcv.imgQuality.ImageQualityHandle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author shenlai
 * @Description TODO
 * @create 2021/3/4 11:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageQualityHandleTest {

    @Test
    public void testImgQuality() throws IOException {

        //下载图片
        //ImageQualityHandle.downLoadImg();

        //计算清晰度 Laplacian梯度方法：
        //ImageQualityHandle.clarityExceptionV3();

        //计算清晰度 矩阵方差
        ImageQualityHandle.fangcha();


        //计算清晰度 Tenengrad梯度方法
        //ImageQualityHandle.tenengrad();


        //测试图片
        //ImageQualityHandle.clarityExceptionV2();

        //ImageQualityHandle.clarityException();

        Assert.assertTrue(true);
    }


    @Test
    public void testImageQuality() throws IOException {


        //计算清晰度 Laplacian梯度方法：
        //ImageQuality.Laplacian();

        ImageQuality.LaplacianV2();


        Assert.assertTrue(true);
    }


    @Test
    public void testJVMException() throws IOException {


        //计算清晰度 Laplacian梯度方法：
        //ImageQuality.Laplacian();

        ImageQuality.LaplacianV2();


        Assert.assertTrue(true);
    }
}

