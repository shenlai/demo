package com.sl.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

/**
 * redis注册
 * @author zouqi
 * @version Id: XcShopRedisConnectionConfiguration, v 0.1 2020/7/16 18:02 zouqi Exp $
 */
@Slf4j
@Configuration
//@ConditionalOnClass(RedisCache.class)
public class XcRedisConnectionConfiguration {

    @Autowired
    private RedisConfigProperties redisConfigProperties;

    /**
     * 默认实例
     * @return
     */
    @Bean("stringRedisTemplate")
    @Primary
    public StringRedisTemplate stringRedisTemplate() {
        Assert.notNull(redisConfigProperties,"redis RedisConfigProperties empty.");

        return RedisConnectionConfigBuilder.build(redisConfigProperties.getHost(),redisConfigProperties.getPort(),redisConfigProperties.getIndex(),redisConfigProperties.getPassword()).buildTemplate();
    }



}
