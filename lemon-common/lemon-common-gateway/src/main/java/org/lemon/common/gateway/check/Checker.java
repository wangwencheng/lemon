package org.lemon.common.gateway.check;

import org.springframework.web.server.ServerWebExchange;

/**
 * 校验器
 * @author wwc
 */
public interface Checker {
	 void check(ServerWebExchange exchange);
}
