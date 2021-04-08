package com.sl.cache.config;

/**
 * @author zouqi
 * @version Id: CacheName, v 0.1 2020/7/16 21:51 zouqi Exp $
 */
public interface CacheName {
    String MANAGER_REDIS = "cache_manager_redis";
    String MANAGER_REDIS_DOUBLE_CACHE = "cache_manager_redis_double";
    String MANAGER_NATIVE = "cache_manager_native_caffeine";
}
