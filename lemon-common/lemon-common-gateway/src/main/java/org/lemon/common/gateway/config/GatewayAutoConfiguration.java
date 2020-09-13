package org.lemon.common.gateway.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.gateway.check.Checker;
import org.lemon.common.gateway.check.GatewayChecker;
import org.lemon.common.gateway.param.GatewayParamBuilder;
import org.lemon.common.gateway.param.ParamBuilder;
import org.lemon.common.gateway.properties.ApiProperties;
import org.lemon.common.gateway.result.GatewayResultExecutor;
import org.lemon.common.gateway.result.ResultExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 网关基础配置
 * @author Donald
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class GatewayAutoConfiguration {
	private final ApiProperties properties;
	private final RedisTemplate redisTemplate;

	@Bean
	public Checker gatewayChecker() {
		return new GatewayChecker(properties, redisTemplate);
	}

	@Bean
	public ResultExecutor gatewayResultExecutor(){
		return new GatewayResultExecutor(properties);
	}

	@Bean
	public ParamBuilder paramBuilder() {
		return new GatewayParamBuilder();
	}

}
