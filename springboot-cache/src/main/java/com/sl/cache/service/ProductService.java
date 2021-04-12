package com.sl.cache.service;

import com.sl.cache.config.CacheName;
import com.sl.cache.entity.Product;
import com.sl.cache.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.sl.cache.config.XcRedisAutoConfiguration.bizCache_600_sec;
import static com.sl.cache.config.XcRedisAutoConfiguration.bizCache_60_sec;

@Service
//
//@CacheConfig(cacheNames = "product")
public class ProductService {
    //@Autowired
    private ProductMapper productMapper;


    @Cacheable(cacheManager = CacheName.MANAGER_REDIS_DOUBLE_CACHE, value = bizCache_60_sec, keyGenerator = "key",sync = true)
    public Product getProductByIdV2(Long id) {
        Product product = new Product();
        Random random = new Random();
        product.setProductId((long) random.nextInt(100));
        System.out.println("执行方法：" + product);
        return product;
    }

//    @Cacheable(cacheNames = "product1",key = "#root.methodName+'['+#id+']'")
//    //@Cacheable(cacheNames = {"product1","product2"})// 默认key为参数，多个参数SimpleKey [arg1,arg2]
//    //@Cacheable(cacheNames = "product",key = "#root.methodName+'['+#id+']'")
//    //@Cacheable(cacheNames = "product",keyGenerator = "myKeyGenerator")
//    //@Cacheable(cacheNames = "product",key = "#root.methodName+'['+#id+']'",condition="#a0>10",unless = "#a0==11") //或者condition="#id>10")
//    public Product getProductById(Long id){
//       Product product =productMapper.getProductById(id);
//       System.out.println(product);
//       return product;
//    }
//
//
//    /**
//     * @CachePut 既调用方法，又更新缓存
//     *
//     *
//     */
//
//    //@CachePut
//    @CachePut(value="product",key = "#result.productId",condition = "#result!=null")
//    public  Product updateProduct(Product product){
//        int count = productMapper.updateProduct(product);
//        System.out.println("影响行数："+count);
//        if(count>0){
//            return product;
//        }else{
//            return null;
//        }
//    }
//
//    //@CacheEvict(value="product",key="#id")
//    //@CacheEvict(value="product",allEntries = true) //清楚所有缓存
//    @CacheEvict(value="product",allEntries = true,beforeInvocation = true) //清楚所有缓存
//    public boolean deleteProductById(Long id) {
//        productMapper.deleteProductById(id);
//        return true;
//    }
//
//    //含有CachePut注解，所以执行这个方法时一定会查询数据库，及时有cacheable注解
//    @Caching(
//            cacheable = {@Cacheable(value="product",key="#productName")},
//            put = {
//                    @CachePut(value="product",key="#result.productId"),
//                    @CachePut(value="product",key="#result.productName")
//            }
//    )
//    public  Product getProductByName(String productName){
//
//        Product product =productMapper.getProductByName(productName);
//
//         return product;
//    }
}
