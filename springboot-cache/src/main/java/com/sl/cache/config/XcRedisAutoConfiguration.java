package com.sl.cache.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.google.common.collect.Sets;
import com.google.common.hash.Hashing;
import com.sl.cache.doublecache.RedisDoubleCacheManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * redis自动装配
 *
 * @author zouqi
 * @version Id: XCRedisAutoConfiguration, v 0.1 2020/7/15 15:19 zouqi Exp $
 */
@Configuration
//@ConditionalOnClass(RedisCache.class)
@EnableConfigurationProperties(RedisConfigProperties.class)
//@ConditionalOnProperty(name = XcShopConfigConstants.REDIS_ENABLED, matchIfMissing = true)
@Import({XcRedisConnectionConfiguration.class})
public class XcRedisAutoConfiguration {

    public static final String bizCache_60_sec = "bizCache_60_sec";  // redis公共缓存
    public static final String bizCache_300_sec = "bizCache_300_sec"; // redis公共缓存
    public static final String bizCache_600_sec = "bizCache_600_sec"; // redis公共缓存

//    /**
//     * 实例redis
//     *
//     * @return
//     */
//    @Bean("redisCache")
//    public RedisCache redisCache(StringRedisTemplate stringRedisTemplate) {
//        RedisCacheImpl redisCache = new RedisCacheImpl();
//        redisCache.setRedisTemplate(stringRedisTemplate);
//        return redisCache;
//    }

    /**
     * 实例本地缓存
     *
     * @return
     */
    @Bean(value = CacheName.MANAGER_NATIVE)
    public CacheManager caffeineCacheManager() {

        SimpleCacheManager manager = new SimpleCacheManager();

//        List<CaffeineCache> caches = Arrays.asList(
//            new CaffeineCache(bizCache_60_sec, Caffeine.newBuilder().recordStats().expireAfterWrite(60, TimeUnit.SECONDS).maximumSize(100000).build()),
//            new CaffeineCache(bizCache_300_sec, Caffeine.newBuilder().recordStats().expireAfterWrite(300, TimeUnit.SECONDS).maximumSize(100000).build()),
//            new CaffeineCache(bizCache_600_sec, Caffeine.newBuilder().recordStats().expireAfterWrite(600, TimeUnit.SECONDS).maximumSize(100000).build()));
//        manager.setCaches(caches);

        return manager;
    }

    /**
     * 实例redis本地缓存
     *
     * @param stringRedisTemplate
     * @return
     */
    @Primary
    @Bean(CacheName.MANAGER_REDIS)
    public CacheManager bizCache(StringRedisTemplate stringRedisTemplate) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(300)).computePrefixWith(s -> "__:")
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()));
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put(bizCache_60_sec, redisCacheConfiguration.entryTtl(Duration.ofSeconds(60)));
        configMap.put(bizCache_300_sec, redisCacheConfiguration.entryTtl(Duration.ofSeconds(300)));
        configMap.put(bizCache_600_sec, redisCacheConfiguration.entryTtl(Duration.ofSeconds(600)));

        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(stringRedisTemplate.getConnectionFactory())).cacheDefaults(redisCacheConfiguration)
                .initialCacheNames(Sets.newHashSet(bizCache_300_sec)).withInitialCacheConfigurations(configMap).build();
    }

    @Bean(CacheName.MANAGER_REDIS_DOUBLE_CACHE)
    public CacheManager bizDoubleCache(StringRedisTemplate stringRedisTemplate) {
        Map<String, Long> configMap = new HashMap<>();
        configMap.put(bizCache_60_sec, 60L);
        configMap.put(bizCache_300_sec, 300L);
        configMap.put(bizCache_600_sec, 600L);
        return new RedisDoubleCacheManager(stringRedisTemplate, Sets.newHashSet(bizCache_300_sec), configMap, "second:key");
    }

    /**
     * 本地缓存自动生成key
     *
     * @return
     */
    @Bean
    public KeyGenerator key() {
        return (target, method, params) -> {
            if (params.length == 0) {
                return method.getDeclaringClass().getSimpleName() + ":" + method.getName();
            } else {
                if (params.length == 1) {
                    Object param = params[0];
                    if (param != null && !param.getClass().isArray()) {
                        String key = method.getDeclaringClass().getSimpleName() + ":" + method.getName() + ":" + param;
                        System.out.println("当亲前key:" + key);
                        return key;
                    }
                }

                String md5Key = Hashing.md5().newHasher().putString(StringUtils.arrayToCommaDelimitedString(params), Charset.defaultCharset()).hash().toString();
                String key = method.getDeclaringClass().getSimpleName() + ":" + method.getName() + ":[" + md5Key + "]";
                System.out.println("当亲前key:" + key);
                return key;
            }
        };
    }

}
