package com.sl.cache.doublecache;

import com.sl.cache.config.ExecutorsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @version 1.0.0
 * @shenlai
 * @date 2020-11-06
 */
@Slf4j
public class RedisDoubleCacheBF extends RedisCache {

    private final String name;
    private final RedisCacheWriter cacheWriter;
    private final RedisCacheConfiguration cacheConfig;
    private Map<String, Long> expires;
    private final ConversionService conversionService;
    //二级缓存前缀
    private String secondCachePrefix;

    //TODO caffeine实现,指定size
    private Map<String, ReentrantLock> keyLockMap = new ConcurrentHashMap<String, ReentrantLock>();


    public RedisDoubleCacheBF(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig, Map<String, Long> configMap, String secondCachePrefix) {
        super(name, cacheWriter, cacheConfig);
        this.name = name;
        this.cacheWriter = cacheWriter;
        this.cacheConfig = cacheConfig;
        this.conversionService = cacheConfig.getConversionService();
        this.expires = configMap;
        this.secondCachePrefix = Strings.isEmpty(secondCachePrefix) ? "_:" : secondCachePrefix;
    }

    /**
     * 一级缓存
     *
     * @param key
     * @return
     * @deprecated
     */
    @Override
    protected Object lookup(Object key) {
        byte[] value = cacheWriter.get(name, createAndConvertCacheKey(key));
        if (value == null) {
            return null;
        }
        return deserializeCacheValue(value);
    }


    private byte[] createAndConvertCacheKey(Object key) {
        return serializeCacheKey(createCacheKey(key));
    }


    /**
     * 二级级缓存
     *
     * @param key
     * @return
     */
    private <T> Object lookupSecond(Object key, Callable<T> valueLoader) {
        String secondKey = getSecondKey(key);
        byte[] value = cacheWriter.get(name, createAndConvertCacheKey(secondKey));
        try {
            //异步刷新一级缓存
            if (value != null) {
                ExecutorsUtils.getExecutorSecondCache().execute(() -> {
                    try {
                        log.info("update first and second cache, the key is : {}", key);
                        //执行回调
                        T newValue = valueLoader.call();
                        //刷新一级缓存、刷新二级缓存
                        put(key, newValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e.getCause());
        }
        if (value == null) {
            return null;
        }
        return deserializeCacheValue(value);
    }

    /**
     * 二级级缓存
     *
     * @param key
     * @return
     */
    private <T> Object lookupSecondV2(Object key, Callable<T> valueLoader) {
        String secondKey = getSecondKey(key);
        byte[] value = cacheWriter.get(name, createAndConvertCacheKey(secondKey));
        try {
            //异步刷新一级缓存
            if (value != null) {
                ExecutorsUtils.getExecutorSecondCache().execute(() -> {
                    ReentrantLock lock = getLock(key.toString());
                    try {
                        lock.lock();
                        ValueWrapper res = this.get(key);
                        if (value != null) {
                            log.info("async exist");
                            return;
                        }
                        log.info("async update key: {}", key);
                        //执行回调
                        T newValue = valueLoader.call();
                        //刷新一级缓存、刷新二级缓存
                        put(key, newValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                        keyLockMap.remove(key);
                    }
                });
            }
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e.getCause());
        }
        if (value == null) {
            return null;
        }
        return deserializeCacheValue(value);
    }

    public <T> ValueWrapper getSecond(Object key, Callable<T> valueLoader) {
        Object value = lookupSecond(key, valueLoader);
        return toValueWrapper(value);
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    public <T> T get(Object key, Callable<T> valueLoader) {
//        //一级缓存
//        ValueWrapper value = this.get(key);
//        if (value != null) {
//            return (T) value.get();
//        }
//        try {
//            value = getSecond(key, valueLoader);
//            if (value != null) {
//                return (T) value.get();
//            }
//            //一级、二级为空 ReentrantLock 同步执行回调
//            T newValue = asyncCache(key, valueLoader);
//            return newValue;
//        } catch (Exception e) {
//            throw new ValueRetrievalException(key, valueLoader, e.getCause());
//        } finally {
//
//        }
//    }

    private ReentrantLock getLock(String key) {
        ReentrantLock lock = keyLockMap.get(key.toString());
        if (lock == null) {
            log.info("create lock for key : {}", key);
            lock = new ReentrantLock();
            keyLockMap.putIfAbsent(key.toString(), lock);
            lock = keyLockMap.get(key.toString());
        }
        return lock;
    }

    /**
     * 同步刷新缓存
     *
     * @param key
     * @param valueLoader
     * @param <T>
     * @return
     */
    private <T> T asyncCache(Object key, Callable<T> valueLoader) {
        ReentrantLock lock = getLock(key.toString());
        try {
            lock.lock();
            //一级缓存
            ValueWrapper value = this.get(key);
            if (value != null) {
                return (T) value.get();
            }
            //执行目标方法
            T newValue = valueLoader.call();
            put(key, newValue);
            return newValue;
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e.getCause());
        } finally {
            lock.unlock();
            keyLockMap.remove(key.toString());
        }

    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        //一级缓存
        ValueWrapper value = this.get(key);
        if (value != null) {
            return (T) value.get();
        }

        ReentrantLock lock = keyLockMap.get(key.toString());
        if (lock == null) {
            log.info("create lock for key : {}", key);
            lock = new ReentrantLock();
            keyLockMap.putIfAbsent(key.toString(), lock);
            lock = keyLockMap.get(key.toString()); //并发
        }
        try {
            lock.lock();
            value = get(key);
            if (value != null) {
                return (T) value.get();
            }
            value = getSecond(key, valueLoader);
            if (value != null) {
                return (T) value.get();
            }
            //同步执行回调
            T newValue = valueLoader.call();
            put(key, newValue);
            return newValue;
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e.getCause());
        } finally {
            lock.unlock();
            keyLockMap.remove(key.toString());
        }
    }



    @Override
    public void put(Object key, Object value) {
        Object cacheValue = preProcessCacheValue(value);
        if (!isAllowNullValues() && cacheValue == null) {
            throw new IllegalArgumentException(String.format(
                    "Cache '%s' does not allow 'null' values. Avoid storing null via '@Cacheable(unless=\"#result == null\")' or configure RedisCache to allow 'null' via RedisCacheConfiguration.",
                    name));
        }
        Long cacheNameExpire = expires.get(this.name);
        Duration duration = Duration.ofSeconds(cacheNameExpire, 0);
        cacheWriter.put(name, createAndConvertCacheKey(key), serializeCacheValue(cacheValue), duration);

        String secondKey = getSecondKey(key);
        Duration duration2 = Duration.ofSeconds(cacheNameExpire * 10, 0);
        cacheWriter.put(name, createAndConvertCacheKey(secondKey), serializeCacheValue(cacheValue), duration2);

    }

    /**
     * 获取二级缓存key
     *
     * @param key
     * @return
     */
    private String getSecondKey(Object key) {
        return secondCachePrefix.concat(key.toString());
    }


}
