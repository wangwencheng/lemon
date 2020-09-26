package org.lemon.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.gateway.constant.GatewayConstant;
import org.lemon.common.gateway.support.GatewayContext;
import org.lemon.common.gateway.util.RequestUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 打印请求参数及统计执行时长过滤器
 *
 * @author wwc
 */
@Slf4j
@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

	private static final String START_TIME = "startTime";

	public LoggingGlobalFilter() {
		log.info("Loaded GlobalFilter [Logging]");
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		GatewayContext gatewayContext = exchange.getAttribute(GatewayConstant.CACHE_GATEWAY_CONTEXT);
		Map<String, String> params = new HashMap<>();
		if (gatewayContext != null) {
			params = gatewayContext.getAllRequestData().toSingleValueMap();
		}
		String info = String.format("Method:{%s} Host:{%s} Path:{%s} Ip:{%s} Headers:%s Params:%s",
				exchange.getRequest().getMethod().name(),
				exchange.getRequest().getURI().getHost(),
				exchange.getRequest().getURI().getPath(),
				RequestUtil.getIpAddress(exchange.getRequest()),
				JSONObject.toJSON(exchange.getRequest().getHeaders().toSingleValueMap()),
				JSONObject.toJSON(params));
		log.info(info);
		exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			Long startTime = exchange.getAttribute(START_TIME);
			if (startTime != null) {
				Long executeTime = (System.currentTimeMillis() - startTime);
				log.info(exchange.getRequest().getURI().getRawPath() + " : " + executeTime + "ms");
			}
		}));
	}

	@Override
	public int getOrder() {
		return -100;
	}


}