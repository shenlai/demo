package com.sl.cache.service;

import com.sl.cache.entity.Product;
import com.sl.cache.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
//
@CacheConfig(cacheNames = "product")
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Cacheable(cacheNames = "product1",key = "#root.methodName+'['+#id+']'")
    //@Cacheable(cacheNames = {"product1","product2"})// 默认key为参数，多个参数SimpleKey [arg1,arg2]
    //@Cacheable(cacheNames = "product",key = "#root.methodName+'['+#id+']'")
    //@Cacheable(cacheNames = "product",keyGenerator = "myKeyGenerator")
    //@Cacheable(cacheNames = "product",key = "#root.methodName+'['+#id+']'",condition="#a0>10",unless = "#a0==11") //或者condition="#id>10")
    public Product getProductById(Long id){
       Product product =productMapper.getProductById(id);
       System.out.println(product);
       return product;
    }


    /**
     * @CachePut 既调用方法，又更新缓存
     *
     *
     */

    //@CachePut
    @CachePut(value="product",key = "#result.productId",condition = "#result!=null")
    public  Product updateProduct(Product product){
        int count = productMapper.updateProduct(product);
        System.out.println("影响行数："+count);
        if(count>0){
            return product;
        }else{
            return null;
        }
    }

    //@CacheEvict(value="product",key="#id")
    //@CacheEvict(value="product",allEntries = true) //清楚所有缓存
    @CacheEvict(value="product",allEntries = true,beforeInvocation = true) //清楚所有缓存
    public boolean deleteProductById(Long id) {
        productMapper.deleteProductById(id);
        return true;
    }

    //含有CachePut注解，所以执行这个方法时一定会查询数据库，及时有cacheable注解
    @Caching(
            cacheable = {@Cacheable(value="product",key="#productName")},
            put = {
                    @CachePut(value="product",key="#result.productId"),
                    @CachePut(value="product",key="#result.productName")
            }
    )
    public  Product getProductByName(String productName){

        Product product =productMapper.getProductByName(productName);

         return product;
    }
}
