package com.sl.springbootdemo;

import com.sl.springbootdemo.utils.DemoRetryStrategy;
import com.sl.springbootdemo.utils.HttpClientUtils;
import com.sl.springbootdemo.utils.UnavailableRetryStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpClientTest {

    @Test
    public void getTest() throws IOException {

        //String url = "http://localhost:8081/dest-hotel-dock/disneyHotel/getTest";
        String url = "http://localhost:8082/dest-hotel-dock/disneyHotel/getTest";

        String res = HttpClientUtils.get(url);

        System.out.println(res);
    }

    @Test
    public void getRetryTest() throws IOException {

        String url = "http://localhost:8081/dest-hotel-dock/disneyHotel/getTest";

        String res = HttpClientUtils.getRetry(url,new UnavailableRetryStrategy(3,200));

        System.out.println(res);
    }


    @Test
    public void getRetryTest2() throws IOException {

        String url = "http://localhost:8081/dest-hotel-dock/disneyHotel/getTest";

        String res = HttpClientUtils.getRetry(url,new DemoRetryStrategy());

        System.out.println(res);
    }

    @Test
    public void TestJavaLasnguage() throws IOException {
        DemoRetryStrategy instance = new DemoRetryStrategy();
        instance.retryRequest(null,2,null);

    }




}
