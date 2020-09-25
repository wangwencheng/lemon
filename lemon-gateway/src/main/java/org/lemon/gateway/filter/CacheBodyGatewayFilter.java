package org.lemon.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.lemon.common.gateway.constant.GatewayConstant;
import org.lemon.common.gateway.support.GatewayContext;
import org.lemon.common.gateway.util.RequestUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * 缓存请求体信息
 *
 * @author wwc
 */
//@Component
public class CacheBodyGatewayFilter implements Ordered, GlobalFilter {

	private static final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		GatewayContext gatewayContext = new GatewayContext();
		HttpHeaders headers = request.getHeaders();
		gatewayContext.setRequestHeaders(headers);
		gatewayContext.getAllRequestData().addAll(request.getQueryParams());
		exchange.getAttributes().put(GatewayConstant.CACHE_GATEWAY_CONTEXT, gatewayContext);
		MediaType contentType = headers.getContentType();
		if (headers.getContentLength() > 0) {
			if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
				return ServerWebExchangeUtils.cacheRequestBodyAndRequest(exchange, (serverHttpRequest) -> {
					return ServerRequest.create(exchange.mutate().request(serverHttpRequest).build(), messageReaders).bodyToMono(String.class).doOnNext((objectValue) -> {
						if (objectValue != null) {
							gatewayContext.setRequestBody(objectValue);
							gatewayContext.getAllRequestData().setAll(JSONObject.parseObject(objectValue, new TypeReference<Map<String, String>>() {
							}));
						}
					});
				}).then(chain.filter(exchange));
			}

			if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType)) {
				return ServerWebExchangeUtils.cacheRequestBodyAndRequest(exchange, (serverHttpRequest) -> {
					return ServerRequest.create(exchange.mutate().request(serverHttpRequest).build(), messageReaders).bodyToMono(String.class).doOnNext((objectValue) -> {
						if (objectValue != null) {
							MultiValueMap multiValueMap = RequestUtil.parseQueryToMap(objectValue);
							gatewayContext.setFormData(multiValueMap);
							gatewayContext.getAllRequestData().addAll(multiValueMap);
						}
					});
				}).then(chain.filter(exchange));
			}
		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}