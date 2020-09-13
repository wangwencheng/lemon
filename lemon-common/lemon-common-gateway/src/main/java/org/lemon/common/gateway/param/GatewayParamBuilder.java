package org.lemon.common.gateway.param;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.constant.SecurityConstant;
import org.lemon.common.gateway.constant.ErrorMsgConstant;
import org.lemon.common.gateway.constant.GatewayConstant;
import org.lemon.common.gateway.support.GatewayContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.server.ServerWebExchange;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 构造请求参数
 * @author Donald
 */
@Slf4j
public class GatewayParamBuilder implements ParamBuilder {
	private static final String PASSWORD = "password";
	private static final String KEY_ALGORITHM = "AES";
	@Value("${security.encode.key:1234567812345678}")
	private String encodeKey;

	@Override
	public Map<String,String> build(ServerWebExchange exchange) {
		Map<String, String> originalParams = new HashMap<String, String>();
		GatewayContext gatewayContext = exchange.getAttribute(GatewayConstant.CACHE_GATEWAY_CONTEXT);
		if (gatewayContext != null) {
			originalParams = gatewayContext.getAllRequestData().toSingleValueMap();
		}
		if (originalParams.isEmpty()) {
			return originalParams;
		}

		if (StrUtil.containsAnyIgnoreCase(exchange.getRequest().getURI().getPath(), SecurityConstant.OAUTH_TOKEN_URL)) {
			String password = originalParams.get(PASSWORD);
			if (StrUtil.isNotBlank(password)) {
				try {
					password = decryptAES(password, encodeKey);
				} catch (Exception e) {
					log.error("密码解密失败:{}", password);
					throw new RuntimeException(ErrorMsgConstant.DECRYPT_ILLEGAL_PASSWORD);
				}
				originalParams.put(PASSWORD, password.trim());
			}
		}

		String paramStr = originalParams.get(GatewayConstant.PARAMS_KEY);
		if (!StrUtil.isEmpty(paramStr)) {
			originalParams.clear();
			originalParams.putAll(JSONObject.parseObject(paramStr, new TypeReference<Map<String, String>>() {
			}));
		}else{
			originalParams.remove(GatewayConstant.SIGN_KEY);
			originalParams.remove(GatewayConstant.TIMESTAMP_KEY);
			originalParams.remove(GatewayConstant.NONCE_KEY);
		}
		return originalParams;
	}

	@SneakyThrows
	private static String decryptAES(String data, String pass) {
		AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,
				new SecretKeySpec(pass.getBytes(), KEY_ALGORITHM),
				new IvParameterSpec(pass.getBytes()));
		byte[] result = aes.decrypt(Base64.decode(data.getBytes(StandardCharsets.UTF_8)));
		return new String(result, StandardCharsets.UTF_8);
	}

}
