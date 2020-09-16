package org.lemon.common.gateway.param;

import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * 构造请求参数
 * @author wwc
 */
public interface ParamBuilder {

	Map<String,String> build(ServerWebExchange exchange);

}
