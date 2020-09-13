package org.lemon.common.gateway.util;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.gateway.constant.ErrorMsgConstant;
import org.lemon.common.gateway.constant.GatewayConstant;
import org.lemon.common.gateway.constant.enums.SignTypeEnum;

import java.util.*;

/**
 * 签名工具类
 *
 * @author Donald
 */
@Slf4j
public class SignUtil {
	public static void main(String[] args) {
		String secret = "6zm1pbsYpvfWmOmuNdV7fofAi9YcMOLW";
		HashMap<String, String> signMap = new HashMap<String, String>();
//		JSONObject js = new JSONObject();
//		js.put("id", "1");
//		js.put("author", "Donald");
//		js.put("moduleName", "global");
//		js.put("packageName", "org.elib");
//		js.put("tableName", "tenant_info");
//		js.put("tablePrefix", "tenant_");

//		signMap.put("id", "1");
//		signMap.put("author", "Donald");
//		signMap.put("moduleName", "global");
//		signMap.put("packageName", "org.elib");
//		signMap.put("tableName", "tenant_info");
//		signMap.put("tablePrefix", "tenant_");

//		signMap.put("username", "OMS@admin1");
//		signMap.put("password", "Bo2dh6yQ71AlXeGuaeOH6g==");
//		signMap.put("grant_type", "password");
//		signMap.put("scope", "app");
//		signMap.put("deviceId", "123456");

		signMap.put("params", "{\"tablePrefix\":\"tenant_\",\"author\":\"Donald\",\"moduleName\":\"global\",\"id\":1,\"packageName\":\"org.elib\",\"tableName\":\"tenant_info\"}");
		signMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		signMap.put("nonce", RandomUtil.randomString(16));
		System.out.println(signMap);
		System.out.println(sign(signMap, "MD5", secret));
	}

	/**
	 * 签名
	 *
	 * @param params
	 * @param signType
	 * @param secretKey
	 * @return
	 */
	public static String sign(Map<String, String> params, String signType, String secretKey) {
		if (params == null) {
			return "";
		}
		SignTypeEnum type = SignTypeEnum.fromType(signType);
		if (type == null) {
			throw new IllegalArgumentException(ErrorMsgConstant.SIGN_ILLEGAL_TYPE);
		}

		// 第一步：参数排序
		Set<String> keySet = params.keySet();
		List<String> paramNames = new ArrayList<>(keySet);
		Collections.sort(paramNames);

		// 第二步：把所有参数名和参数值串在一起
		StringBuilder paramNameValue = new StringBuilder();
		for (String paramName : paramNames) {
			if (paramName.equals(GatewayConstant.SIGN_KEY)) {
				continue;
			}
			if (params.get(paramName) != null && StrUtil.isNotEmpty(String.valueOf(params.get(paramName)).trim())) {
				paramNameValue.append(paramName).append(StringPool.EQUALS).append(params.get(paramName).trim()).append(StringPool.AMPERSAND);
			}
		}
		paramNameValue.append(GatewayConstant.SIGN_SECRET_KEY + StringPool.EQUALS).append(secretKey);

		// 第三步：使用MD5/SHA256加密
		String source = paramNameValue.toString();
		String signStr = "";
		switch (type) {
			case MD5:
				signStr = DigestUtil.md5Hex(source).toUpperCase();
				break;
			case SHA256:
				signStr = DigestUtil.sha256Hex(source).toUpperCase();
				break;
			default:
				break;
		}
		return signStr;
	}

	/**
	 * 验签
	 *
	 * @param params
	 * @param signType
	 * @param secretKey
	 * @return
	 */
	public static boolean verify(Map<String, String> params, String signType, String secretKey) {
		String oldSign = params.get(GatewayConstant.SIGN_KEY);
		if (StrUtil.isEmpty(oldSign)) {
			throw new IllegalArgumentException(ErrorMsgConstant.SIGN_MISSING_SIGNATURE);
		}
		log.info("参数签名验证:{}", oldSign);
		String newSign = sign(params, signType, secretKey);
		if (oldSign.equals(newSign)) {
			return true;
		}
		return false;
	}
}
