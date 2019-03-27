package com.sl.demo.springbootjdbc;

import com.sl.demo.springbootjdbc.entity.Product;
import com.sl.demo.springbootjdbc.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJdbcApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private ProductService productService;

    @Test
    public void test() {
        List<Product> list = productService.queryAllStudent();
        Assert.assertTrue(list.size() > 0);

    }

}
