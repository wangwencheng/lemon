package org.lemon.gateway.filter;

import lombok.AllArgsConstructor;
import org.lemon.common.gateway.check.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关校验过滤器
 *
 * @author wwc
 */
//@Component
@AllArgsConstructor
public class CheckGatewayFilter implements Ordered, GlobalFilter {
	private Checker gatewayChecker;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		try {
			//忽略文件上传请求
			MediaType contentType = exchange.getRequest().getHeaders().getContentType();
			if (contentType == null || MediaType.MULTIPART_FORM_DATA.isCompatibleWith(contentType)) {
				return chain.filter(exchange);
			}
			gatewayChecker.check(exchange);
		} catch (Exception e) {
			return Mono.error(e);
		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1000;
	}
}
