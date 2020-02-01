package com.lessmore.front.mapper;

import com.alibaba.fastjson.JSON;
import com.lessmore.front.model.entity.Product;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTest {

    @Resource
    private ProductMapper productMapper;

    @Test
    public void getProductList() {

        List<Product> productList = productMapper.getProductList();
        System.out.print("******************productList**********:"+ JSON.toJSONString(productList));
        //log.info("212");
    }
}