package com.sl.cache.config;

import com.sl.cache.entity.Product;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.config.CacheManagementConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;
import java.time.Duration;

@Configuration
public class MyRedisConfig {

    /*
    @Bean
    public RedisTemplate<Object, Product> prdRedisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Product> template = new RedisTemplate<Object, Product>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Product> ser = new Jackson2JsonRedisSerializer<Product>(Product.class);
        template.setDefaultSerializer(ser);
        return template;
    }
    */



    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());

        //System.out.println("自定义RedisTemplate加载完成");
        return redisTemplate;
    }


    //参考：https://blog.csdn.net/sy793314598/article/details/80719224
    @Primary
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        //缓存配置对象
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(30L)) //设置缓存的默认超时时间：30分钟
                .disableCachingNullValues()             //如果是空值，不缓存
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer((valueSerializer())));

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }



    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }


    /*
    //CacheManagerCustomizers可以来定制缓存的一些规则
    //@Primary  //将某个缓存管理器作为默认的
    @Bean
    public RedisCacheManager productCacheManager(RedisTemplate<Object, Product> redisTemplate){
        //RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //key多了一个前缀

        //使用前缀，默认会将CacheName作为key的前缀
        //cacheManager.setUsePrefix(true);

        //return cacheManager;
    }
*/


}
