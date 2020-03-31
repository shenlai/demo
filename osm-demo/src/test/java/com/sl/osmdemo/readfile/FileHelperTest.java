package com.sl.osmdemo.readfile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FileHelperTest {

    @Autowired
    private FileHelper fileHelper;

    @Test
    public void tools() {
        List<File> files = new ArrayList<>();
        files.add(new File("E:\\图片数据处理\\跑size文件\\hotel_photo_0.txt.log"));
        files.add(new File("E:\\图片数据处理\\跑size文件\\hotel_photo_1.txt.log"));
        files.add(new File("E:\\图片数据处理\\跑size文件\\hotel_photo_2.txt.log"));
        files.add(new File("E:\\图片数据处理\\跑size文件\\hotel_photo_3.txt.log"));
        files.add(new File("E:\\图片数据处理\\跑size文件\\hotel_photo_4.txt.log"));
        files.add(new File("E:\\图片数据处理\\跑size文件\\hotel_photo_5.txt.log"));
        files.add(new File("E:\\图片数据处理\\跑size文件\\hotel_photo_6.txt.log"));
        files.add(new File("E:\\图片数据处理\\跑size文件\\hotel_photo_7.txt.log"));
        files.add(new File("E:\\图片数据处理\\跑size文件\\hotel_photo_8.txt.log"));
        files.add(new File("E:\\图片数据处理\\跑size文件\\hotel_photo_9.txt.log"));
        files.add(new File("E:\\图片数据处理\\跑size文件\\hotel_photo_10.txt.log"));

        ExecutorService executorService = Executors.newFixedThreadPool(11);
        CountDownLatch latch = new CountDownLatch(11);


        for (int i = 0; i <= 10; i++) {
            final int index = i;
            try {
                executorService.execute(() -> {
                            fileHelper.txt2StringV4(files.get(index));
                            latch.countDown();
                        }
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            latch.await(); //模拟等待的情况,不考虑子线程的处理实际
            System.out.println("************执行完成***************");
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("************执行异常***************");
        }
    }


}