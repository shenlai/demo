package com.sl.demo.springbootjdbc.service;

import com.sl.demo.springbootjdbc.entity.Product;
import com.sl.demo.springbootjdbc.entity.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> queryAllStudent() {
        String sql="select * from tb_product";
        List<Product> list = jdbcTemplate.query(sql,new ProductRowMapper());
        return list;
    }
}
