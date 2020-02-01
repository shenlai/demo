package com.lessmore.front.mapper;

import com.lessmore.front.model.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> getProductList();


}
