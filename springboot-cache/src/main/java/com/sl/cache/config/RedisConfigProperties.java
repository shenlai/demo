package com.sl.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zouqi
 * @version Id: RedisConfigProperties, v 0.1 2020/7/16 16:53 zouqi Exp $
 */
@Getter
@Setter
@ConfigurationProperties("config.redis")
public class RedisConfigProperties {

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
}
