package com.sl.cache;

import com.sl.cache.entity.Product;
import com.sl.cache.mapper.ProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootCacheApplicationTests {


    @Autowired
    ProductMapper productMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate; //操作 k-v 字符串

    @Autowired
    RedisTemplate redisTemplate;  //k- v 都是对象

    //@Autowired
    //RedisTemplate<Object,Product> prdRedisTemplate;

    /**
     * redis 常见
     * String(字符串) List(列表) Set(集合) Hash(散列) ZSet(有序集合)
     */

    //@Test
    public void test1() {
        stringRedisTemplate.opsForValue().append("StringKey", "字符串数值");
        String value = stringRedisTemplate.opsForValue().get("StringKey");
        System.out.println(value);

    }

    @Test
    public void test2() {
        Product product =  productMapper.getProductById(4L);
        //stringRedisTemplate.opsForValue().set("productid4",product);
        //默认如果保存对象，使用jdk序列化机制，序列化后的数据保存到redis中
        //redisTemplate.opsForValue().set("productid4",product);
        //1.将数据以json的方式保存
        //1.1:自己将对象转换为json
        //xxxxx
        //1.2:修改redisTemplate默认的序列化规则
        redisTemplate.opsForValue().set("produxtid4",product);

    }


    //@Test
    public void contextLoads() {
        Product product = productMapper.getProductById(4L);

        System.out.println(product);
    }

}
