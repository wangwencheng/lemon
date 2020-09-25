package org.lemon.common.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.lemon.common.gateway.check.Checker;
import org.lemon.common.gateway.check.GatewayChecker;
import org.lemon.common.gateway.param.GatewayParamBuilder;
import org.lemon.common.gateway.param.ParamBuilder;
import org.lemon.common.gateway.properties.ApiProperties;
import org.lemon.common.gateway.result.GatewayResultExecutor;
import org.lemon.common.gateway.result.ResultExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 网关基础配置
 *
 * @author Donald
 */
@Slf4j
@Configuration
@ComponentScan("org.elib.common.gateway")
public class GatewayAutoConfiguration {
    @Autowired
    private ApiProperties properties;
    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public Checker gatewayChecker() {
        return new GatewayChecker(properties, redisTemplate);
    }

    @Bean
    public ResultExecutor gatewayResultExecutor() {
        return new GatewayResultExecutor(properties);
    }

    @Bean
    public ParamBuilder paramBuilder() {
        return new GatewayParamBuilder();
    }

}
