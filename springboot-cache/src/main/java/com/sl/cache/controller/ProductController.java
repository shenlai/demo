package com.sl.cache.controller;

import com.sl.cache.entity.Product;
import com.sl.cache.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long id) {

        Product product = productService.getProductByIdV2(id);
        return product;
    }


//    //prooduct?productid=1&productName= &
//    @GetMapping("/product")
//    public Product updateProduct(Product product) {
//        productService.updateProduct(product);
//        return product;
//    }
//
//    @GetMapping("/delproduct")
//    public String delProduct(@RequestParam(value="id") Long id) {
//
//        productService.deleteProductById(id);
//        return "ok";
//    }
//
//    @GetMapping("/product/name/{productName}")
//    public Product getEmpByLastName(@PathVariable("productName") String productName){
//        return productService.getProductByName(productName);
//    }


}
