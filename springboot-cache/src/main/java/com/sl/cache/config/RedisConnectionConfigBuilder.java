package com.sl.cache.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zouqi
 * @version Id: AbstractXcRedisConnectionConfig, v 0.1 2020/7/17 10:37 zouqi Exp $
 */
@Getter
@Setter
@Slf4j
public class RedisConnectionConfigBuilder {

    /**
     * 主机
     */
    private String  host;

    /**
     * 密码
     */
    private String  password;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 本项目DB
     */
    private Integer index;

    /**
     *最大空闲数
     */
    private int     maxIdle                       = 1024;

    /**
     *连接池的最大数据库连接数
     */
    private int     maxTotal                      = 1024;

    /**
     *最大建立连接等待时间
     */
    private int     maxWaitMillis                 = 1000;

    /**
     *逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
     */
    private int     minEvictableIdleTimeMillis    = 300000;

    /**
     *每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
     */
    private int     numTestsPerEvictionRun        = 16;

    /**
     *逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
     */
    private int     timeBetweenEvictionRunsMillis = 3000;

    /**
     *是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
     */
    private boolean testOnBorrow;

    /**
     *在空闲时检查有效性, 默认false
     */
    private boolean testWhileIdle                 = true;

    private RedisConnectionConfigBuilder(String host, String password, Integer port, Integer index) {
        this.host = host;
        this.password = password;
        this.port = port;
        this.index = index;
    }

   public static RedisConnectionConfigBuilder build(String host, Integer port, Integer index, String password) {
        return new RedisConnectionConfigBuilder(host, password, port, index);
    }

    /**
     * 构建实例
     * @return
     */
    public StringRedisTemplate buildTemplate() {

        log.info("Redis register start");
        Assert.notNull(getHost(), "host empty");
        Assert.notNull(getPort(), "port empty");
        Assert.notNull(getIndex(), "index empty");
        Assert.notNull(getPassword(), "password empty");

        JedisConnectionFactory factory = jedisConnectionFactory();
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        log.info("Redis register success");
        return template;
    }

    /**
     * 连接工厂
     * @return
     */
    private JedisConnectionFactory jedisConnectionFactory() {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(getHost());
        redisStandaloneConfiguration.setPort(getPort());
        redisStandaloneConfiguration.setDatabase(getIndex());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(getPassword()));

        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpb = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        jpb.poolConfig(jedisPoolConfig());

        return new JedisConnectionFactory(redisStandaloneConfiguration, jpb.build());
    }

    /**
     * 连接池
     * @return
     */
    private JedisPoolConfig jedisPoolConfig() {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(getMaxIdle());
        // 连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(getMaxTotal());
        // 最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(getMaxWaitMillis());
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(getMinEvictableIdleTimeMillis());
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(getNumTestsPerEvictionRun());
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(getTimeBetweenEvictionRunsMillis());
        // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(isTestOnBorrow());
        // 在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(isTestWhileIdle());
        return jedisPoolConfig;
    }

    public RedisConnectionConfigBuilder setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    public RedisConnectionConfigBuilder setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
        return this;
    }

    public RedisConnectionConfigBuilder setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
        return this;
    }

    public RedisConnectionConfigBuilder setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        return this;
    }

    public RedisConnectionConfigBuilder setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
        return this;
    }

    public RedisConnectionConfigBuilder setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        return this;
    }

    public RedisConnectionConfigBuilder setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
        return this;
    }

    public RedisConnectionConfigBuilder setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
        return this;
    }
}
