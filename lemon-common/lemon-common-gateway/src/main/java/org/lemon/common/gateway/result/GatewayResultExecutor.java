package org.lemon.common.gateway.result;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.gateway.constant.GatewayConstant;
import org.lemon.common.gateway.properties.ApiProperties;
import org.lemon.common.gateway.support.GatewayContext;
import org.lemon.common.gateway.util.SignUtil;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关结果执行器
 * @author wwc
 */
@Slf4j
@AllArgsConstructor
public class GatewayResultExecutor implements ResultExecutor {
	private final ApiProperties apiProperties;

	@Override
	public String mergeResult(ServerWebExchange exchange, String original) {
		if (!apiProperties.isResponseMerge()) {
			return original;
		}
		JSONObject responseData = JSONObject.parseObject(original);
		if (!responseData.containsKey(GatewayConstant.RESPONSE_CODE_KEY) || responseData.getInteger(GatewayConstant.RESPONSE_CODE_KEY) == -1) {
			return original;
		}
		Map<String, String> params = new HashMap<String, String>();
		GatewayContext gatewayContext = exchange.getAttribute(GatewayConstant.CACHE_GATEWAY_CONTEXT);
		if (gatewayContext != null) {
			params = gatewayContext.getAllRequestData().toSingleValueMap();
		}
		// 追加请求随机数
		String nonce = params.get(GatewayConstant.NONCE_KEY);
		if (StrUtil.isNotEmpty(nonce)) {
			responseData.put(GatewayConstant.NONCE_KEY, nonce);
		}
		// 追加服务端签名
		if (apiProperties.getSign().isEnable()) {
			Map<String, String> responseParams = responseData.toJavaObject(new TypeReference<Map<String, String>>() {
			});
			String signStr = SignUtil.sign(responseParams, apiProperties.getSign().getType(), apiProperties.getSign().getSecretKey());
			responseData.put(GatewayConstant.SIGN_KEY, signStr);
		}
		return JSONObject.toJSONString(responseData, SerializerFeature.WriteMapNullValue);
	}
}
