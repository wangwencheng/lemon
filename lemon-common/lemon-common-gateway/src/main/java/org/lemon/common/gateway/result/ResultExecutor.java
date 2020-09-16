package org.lemon.common.gateway.result;

import org.springframework.web.server.ServerWebExchange;

/**
 * 结果执行器
 * @author wwc
 */
public interface ResultExecutor {

	String mergeResult(ServerWebExchange exchange, String original);
}
