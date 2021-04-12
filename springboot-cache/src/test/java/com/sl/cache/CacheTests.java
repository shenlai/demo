package com.sl.cache;

import com.sl.cache.entity.Product;
import com.sl.cache.mapper.ProductMapper;
import com.sl.cache.service.ProductService;
import com.sl.cache.service.RedisLockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

//import org.mybatis.spring.annotation.MapperScan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTests {

    @Autowired
    ProductService productService;

    @Test
    public void testRedisLockService() {
        Product productByIdV2 = productService.getProductByIdV2(1L);

        System.out.println(productByIdV2);


    }

    @Test
    public void testLock2() {

        ReentrantLock lock = new ReentrantLock();
        lock.unlock();

        int i = 0;

    }

    @Test
    public void testLock3() {
        String key = "1213";
        ReentrantLock lock = new ReentrantLock();
        Map<String, ReentrantLock> keyLockMap = new ConcurrentHashMap<String, ReentrantLock>();
        keyLockMap.putIfAbsent(key, lock);
        keyLockMap.remove(key);
        keyLockMap.remove(key);

        int i = 0;

    }



    @Test
    public void testLock() {
        String key = "1213";
        Map<String, ReentrantLock> keyLockMap = new ConcurrentHashMap<String, ReentrantLock>();
        ReentrantLock lock = keyLockMap.get(key.toString());
        if (lock == null) {
            lock = new ReentrantLock();
            keyLockMap.putIfAbsent(key.toString(), lock);
        }

        ReentrantLock lock2 = new ReentrantLock();
        keyLockMap.putIfAbsent(key.toString(), lock2);

        boolean equals = Objects.equals(lock, lock2);

        int i = 0;

    }


}
