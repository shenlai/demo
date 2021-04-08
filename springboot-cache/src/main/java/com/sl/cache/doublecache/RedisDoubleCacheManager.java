package com.sl.cache.doublecache;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @author fuwei.deng
 * @version 1.0.0
 * @date 2018年1月26日 下午5:24:52
 */
public class RedisDoubleCacheManager implements CacheManager {

    private final Logger logger = LoggerFactory.getLogger(RedisDoubleCacheManager.class);

    private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    private StringRedisTemplate stringRedisTemplate;

    private Set<String> cacheNames;

    private Map<String, Long> expiresConfig;

    private String secondCachePrefix;

    public RedisDoubleCacheManager(StringRedisTemplate stringRedisTemplate, Set<String> cacheNames, Map<String, Long> configMap, String secondCachePrefix) {
        super();
        this.stringRedisTemplate = stringRedisTemplate;
        this.cacheNames = cacheNames;
        this.expiresConfig = configMap;
        this.secondCachePrefix = secondCachePrefix;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache != null) {
            logger.info("RedisDoubleCacheManager.getCache cache instance, the cache name is : {}", name);
            return cache;
        }
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(300)).computePrefixWith(s -> "__:")
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()));
        cache = new RedisDoubleCache(name, RedisCacheWriter.nonLockingRedisCacheWriter(stringRedisTemplate.getConnectionFactory()), redisCacheConfiguration, expiresConfig, secondCachePrefix);
        Cache oldCache = cacheMap.putIfAbsent(name, cache);
        logger.info("RedisDoubleCacheManager.create cache instance, the cache name is : {}", name);
        return oldCache == null ? cache : oldCache;
    }


    @Override
    public Collection<String> getCacheNames() {
        return this.cacheNames;
    }

}
