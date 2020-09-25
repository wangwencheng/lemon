package org.lemon.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.constant.CacheConstant;
import org.lemon.common.core.constant.CommonConstant;
import org.lemon.common.core.model.TenantVo;
import org.lemon.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * <p>
 * 全局拦截器，作用所有的微服务
 * <p>
 * 1. 对请求头中参数进行处理 from 参数进行清洗
 * 2. 重写StripPrefix = 1,支持全局
 * <p>
 * 支持swagger添加X-Forwarded-Prefix header  （F SR2 已经支持，不需要自己维护）
 *
 * @author wwc
 */
//@Component
@Slf4j
public class RequestGlobalFilter implements GlobalFilter, Ordered {
	@Autowired
	private RedisTemplate redisTemplate;

	private static final String DOMAIN_SECOND = "([a-zA-Z0-9\\-]*\\.?){2}\\.(com\\.cn|net\\.cn|gov\\.cn|org\\.nz|org\\.cn|com|net|org|gov|cc|biz|info|cn|co|me)";

	public RequestGlobalFilter() {
		log.info("Loaded GlobalFilter [Request]");
	}

	/**
	 * Process the Web request and (optionally) delegate to the next
	 * {@code WebFilter} through the given {@link GatewayFilterChain}.
	 *
	 * @param exchange the current server exchange
	 * @param chain    provides a way to delegate to the next filter
	 * @return {@code Mono<Void>} to indicate when request processing is complete
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 1. 清洗请求头中from 参数
		ServerHttpRequest request = exchange.getRequest().mutate()
				.headers(httpHeaders -> httpHeaders.remove("from"))
				.build();

		// 2. 重写StripPrefix
		addOriginalRequestUrl(exchange, request.getURI());
		String rawPath = request.getURI().getRawPath();
		String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/"))
				.skip(1L).collect(Collectors.joining("/"));
		ServerHttpRequest newRequest = request.mutate()
				.path(newPath)
				.build();
		exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

		//3. 获取租户ID写入请求头中
		String url = exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR).toString();
		Pattern pattern = Pattern.compile(DOMAIN_SECOND);
		Matcher matcher = pattern.matcher(URLUtil.url(url).getHost());
		String domain = "";
		if (matcher.find()) {
			domain = matcher.group(0);
		}
		domain = StrUtil.subBefore(domain, StringPool.DOT, false);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(TenantVo.class));
		TenantVo tenantVo = (TenantVo) redisTemplate.opsForHash().get(CacheConstant.TENANT_KEY, domain);
		if (tenantVo != null) {
			request.mutate().headers(httpHeaders -> httpHeaders.add(CommonConstant.TENANT_ID, tenantVo.getTenantId().toString()));
		} else {
			log.info("租户信息匹配失败:{}", domain);
			ServerHttpResponse response = exchange.getResponse();
			byte[] bytes = JSONUtil.toJsonStr(R.failed(String.format("租户信息匹配失败:%s", domain))).getBytes(StandardCharsets.UTF_8);
			DataBuffer buffer = response.bufferFactory().wrap(bytes);
			response.getHeaders().add("Content-Type", "application/json;charset=utf-8");
			return response.writeWith(Mono.just(buffer));
		}

		return chain.filter(exchange.mutate().request(newRequest.mutate().build()).build());
	}

	@Override
	public int getOrder() {
		return -1000;
	}
}
