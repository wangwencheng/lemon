package org.lemon.common.gateway.check;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.gateway.constant.ErrorMsgConstant;
import org.lemon.common.gateway.constant.GatewayConstant;
import org.lemon.common.gateway.properties.ApiProperties;
import org.lemon.common.gateway.support.GatewayContext;
import org.lemon.common.gateway.util.SignUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 网关校验器
 *
 * @author Donald
 */
@Slf4j
@AllArgsConstructor
public class GatewayChecker implements Checker {
	private final ApiProperties apiProperties;
	private final RedisTemplate redisTemplate;
	private static final Set<String> ignoreCheckUrls = new ConcurrentHashSet<>();
	private static final AntPathMatcher antMatcher = new AntPathMatcher();
	private static final int MILLISECOND_OF_ONE_SECOND = 1000;

	@Override
	public void check(ServerWebExchange exchange) {

		//忽略校验
		if (ignoreCheck(exchange)) {
			return;
		}
		//验签
		checkSign(exchange);
		//防重放
		checkReplayAttack(exchange);
	}

	protected boolean ignoreCheck(ServerWebExchange exchange) {
		String requestPath = exchange.getRequest().getURI().getPath();
		ignoreCheckUrls.add("/favicon.ico");
		ignoreCheckUrls.add("/actuator/**");
		ignoreCheckUrls.add("/**/v2/api-docs/**");
		ignoreCheckUrls.add("/**/swagger-resources/**");
		ignoreCheckUrls.add("/**/static/**");
		ignoreCheckUrls.add("/**/ws/**");
		ignoreCheckUrls.add("/webjars/**");
		for (String path : ignoreCheckUrls) {
			if (antMatcher.match(path, requestPath)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 签名校验
	 *
	 * @param exchange
	 */
	protected void checkSign(ServerWebExchange exchange) {
		if (!apiProperties.getSign().isEnable()) {
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		GatewayContext gatewayContext = exchange.getAttribute(GatewayConstant.CACHE_GATEWAY_CONTEXT);
		if (gatewayContext != null) {
			params = gatewayContext.getAllRequestData().toSingleValueMap();
		}

		if (!SignUtil.verify(params, apiProperties.getSign().getType(), apiProperties.getSign().getSecretKey())) {
			throw new RuntimeException(ErrorMsgConstant.SIGN_INVALID_SIGNATURE);
		}
	}

	/**
	 * 防重放攻击
	 *
	 * @param exchange
	 */
	protected void checkReplayAttack(ServerWebExchange exchange) {
		if (!apiProperties.getReplayAttack().isEnable()) {
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		GatewayContext gatewayContext = exchange.getAttribute(GatewayConstant.CACHE_GATEWAY_CONTEXT);
		if (gatewayContext != null) {
			params = gatewayContext.getAllRequestData().toSingleValueMap();
		}
		String timeStamp = params.get(GatewayConstant.TIMESTAMP_KEY);
		if (StrUtil.isEmpty(timeStamp)) {
			throw new IllegalArgumentException(ErrorMsgConstant.ATTACK_MISSING_TIMESTAMP);
		}
		int timeout = apiProperties.getReplayAttack().getTimeout();
		if ((System.currentTimeMillis() - Long.parseLong(timeStamp)) > timeout * MILLISECOND_OF_ONE_SECOND) {
			throw new RuntimeException(ErrorMsgConstant.ATTACK_INVALID_TIMESTAMP);
		}

		String nonce = params.get(GatewayConstant.NONCE_KEY);
		if (StrUtil.isEmpty(nonce)) {
			throw new IllegalArgumentException(ErrorMsgConstant.ATTACK_MISSING_NONCE);
		}
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		if (redisTemplate.hasKey(GatewayConstant.NONCE_CACHE_KEY + nonce)) {
			throw new RuntimeException(ErrorMsgConstant.ATTACK_INVALID_NONCE);
		}
		redisTemplate.opsForValue().set(GatewayConstant.NONCE_CACHE_KEY + nonce, nonce, timeout, TimeUnit.SECONDS);
	}

}
