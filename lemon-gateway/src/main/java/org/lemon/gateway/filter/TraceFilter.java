package org.lemon.gateway.filter;

import cn.hutool.core.util.IdUtil;
import org.lemon.common.core.constant.CommonConstant;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 日志链路追踪ID，在header中传递
 *
 * @author wwc
 */
@Component
public class TraceFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String traceId = IdUtil.fastSimpleUUID();
		MDC.put(CommonConstant.LOG_TRACE_ID, traceId);
		ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate()
				.headers(httpHeaders -> httpHeaders.add(CommonConstant.HEADER_TRACE_ID, traceId))
				.build();
		ServerWebExchange build = exchange.mutate().request(serverHttpRequest).build();
		return chain.filter(build);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 100;
	}
}
