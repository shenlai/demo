package com.sl.springlearning.tran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public  void insert(){
        String sql ="insert into products(`NAME`,UnitPrice,IsNew) VALUES(?,?,?)";

        jdbcTemplate.update(sql,"小酒店",120,1);

    }

    public  void update(){

        String sql ="update products set Name = ? where Id = 22 ";

        jdbcTemplate.update(sql,"update酒店");

    }



}
