package com.sl.cache.doublecache;

import com.alibaba.fastjson.JSON;
import com.sl.cache.config.ExecutorsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.sl.cache.config.XcRedisAutoConfiguration.*;


public class RedisDoubleCacheV1 extends AbstractValueAdaptingCache {

    private final Logger logger = LoggerFactory.getLogger(RedisDoubleCacheV1.class);

    private String name;

    private StringRedisTemplate stringRedisTemplate;

    private String cachePrefix;

    private long defaultExpiration = 0;

    private Map<String, Long> expires;


    //TODO caffeine实现,指定size
    private Map<String, ReentrantLock> keyLockMap = new ConcurrentHashMap<String, ReentrantLock>();

    public RedisDoubleCacheV1(String name, StringRedisTemplate stringRedisTemplate) {
        super(true);
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
        /** 全局过期时间，单位秒，默认不过期*/
        this.defaultExpiration = 300; //单位秒
        /** 每个cacheName的过期时间，单位秒，优先级比defaultExpiration高*/

        Map<String, Long> configMap = new HashMap<>();
        configMap.put(bizCache_60_sec, 60L);
        configMap.put(bizCache_300_sec, 300L);
        configMap.put(bizCache_600_sec, 600L);
        this.expires = configMap;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
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
        Object value = stringRedisTemplate.opsForValue().get(key);
        logger.info("lookup: get cache from first cache, the key is : {}", key);
        return toValueWrapper(value);
    }

    /**
     * 二级级缓存
     *
     * @param key
     * @return
     */
    private <T> Object lookupSecond(Object key, Callable<T> valueLoader) {
        String secondKey = "second:" + key;
        logger.info("get cache from redis and put in caffeine, the  second key is : {}", secondKey);
        Object value = stringRedisTemplate.opsForValue().get(secondKey);
        try {
            //异步刷新一级缓存
            if (value != null) {
                logger.info("update first and second cache , the  second key is : {}", secondKey);
                ExecutorsUtils.getExecutorPool().execute(() -> {
                    Object newValue = null;
                    try {
                        logger.info("异步刷新 : {}", secondKey);
                        //执行回调
                        newValue = valueLoader.call();
                        //刷新一级缓存、刷新二级缓存
                        Object storeValue = toStoreValue(newValue);
                        put(key, storeValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e.getCause());
        }
        return toValueWrapper(value);
    }

    public ValueWrapper get(Object key) {
        Object value = lookup(key);
        return toValueWrapper(value);
    }
    public <T> ValueWrapper getSecond(Object key,Callable<T> valueLoader) {
        Object value = lookupSecond(key,valueLoader);
        return toValueWrapper(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        //一级缓存
        Object value = lookup(key);
        if (value != null) {
            return (T) value;
        }

        ReentrantLock lock = keyLockMap.get(key.toString());
        if (lock == null) {
            logger.info("create lock for key : {}", key);
            lock = new ReentrantLock();
            keyLockMap.putIfAbsent(key.toString(), lock);
        }

        try {
            lock.lock();
            value = lookup(key);
            if (value != null) {
                return (T) value;
            }
            value = lookupSecond(key, valueLoader);
            if (value != null) {
                return (T) value;
            }
            //同步执行回调
            Object newValue = valueLoader.call();
            //更新缓存
            Object storeValue = toStoreValue(newValue);
            put(key, storeValue);
            return (T) storeValue;
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e.getCause());
        } finally {
            lock.unlock();
            keyLockMap.remove(key.toString());
        }
    }

    @Override
    public void put(Object key, Object value) {
        logger.info("put cache, the key is : {}", key);
        if (!super.isAllowNullValues() && value == null) {
            this.evict(key);
            return;
        }
        //TODO check
        long expire = getExpire();
        String secondKey = "second:" + key;
        if (expire > 0) {
            //配置过期时间
            set((String) key, toStoreValue(value), expire, TimeUnit.SECONDS);
            //二级缓存有效时间 : 一级10倍
            set(secondKey, toStoreValue(value), expire * 10, TimeUnit.SECONDS);
        } else {
            //永久有效
            //stringRedisTemplate.opsForValue().set(key, toStoreValue(value));
            //stringRedisTemplate.opsForValue().set(secondKey, toStoreValue(value));
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        logger.info("putIfAbsent for key : {}", key);
        Object cacheKey = key;
        Object prevValue = null;
        String secondKey = "second:" + key;
        // 考虑使用分布式锁，或者将redis的setIfAbsent改为原子性操作
        synchronized (key) {
            prevValue = stringRedisTemplate.opsForValue().get(cacheKey);
            if (prevValue == null) {
                long expire = getExpire();
                if (expire > 0) {
                    set((String) key, toStoreValue(value), expire, TimeUnit.MILLISECONDS);
                    set(secondKey, toStoreValue(value), expire, TimeUnit.MILLISECONDS);
                } else {
                    //永久有效
                    //stringRedisTemplate.opsForValue().set(getKey(key), toStoreValue(value));
                }
            }
        }
        return toValueWrapper(prevValue);
    }

    private void set(String key, Object result, Long expireTime, TimeUnit unit) {
        if (result instanceof String) {
            stringRedisTemplate.opsForValue().set(key, (String) result, expireTime, unit);
        } else {
            stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(result), expireTime, unit);
            //GenericFastJsonRedisSerializer serializer =  new GenericFastJsonRedisSerializer();
            //stringRedisTemplate.opsForValue().set(key, serializer.serialize(result), expireTime, unit);
        }
    }

    @Override
    public void evict(Object key) {
        // 先清除redis中二级缓存数据，然后清除一级缓存，避免短时间内命中二级缓存
        try {
            String secondKey = "second:" + key;
            stringRedisTemplate.delete(secondKey);
            stringRedisTemplate.delete((String) key);
        } catch (Exception e) {
            logger.error("evict异常", e);
        }
    }

    @Override
    public void clear() {
        // 先清除redis中二级缓存数据，然后清除一级缓存，避免短时间内命中二级缓存
//        Set<Object> keys = stringKeyRedisTemplate.keys(this.name.concat(":*"));
//        for (Object key : keys) {
//            String secondKey = "second:" + key;
//            stringKeyRedisTemplate.delete(secondKey);
//            stringKeyRedisTemplate.delete(key);
//        }
    }


    //TODO
    private Object getKey(Object key) {
        return this.name.concat(":").concat(StringUtils.isEmpty(cachePrefix) ? key.toString() : cachePrefix.concat(":").concat(key.toString()));
    }

    private long getExpire() {
        long expire = defaultExpiration;
        Long cacheNameExpire = expires.get(this.name);
        return cacheNameExpire == null ? expire : cacheNameExpire.longValue();
    }

}
