package org.lemon.common.gateway.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 请求工具类
 * @author Donald
 */
@Slf4j
public class RequestUtil {

	/**
	 * 将查询参数转换成MultiValueMap
	 *
	 * @param query a=1&b=2&c=3
	 * @return
	 */
	public static MultiValueMap<String, String> parseQueryToMap(String query) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(0);
		String[] queryList = StrUtil.split(query, StringPool.AMPERSAND);
		for (String param : queryList) {
			String[] paramArr = param.split("\\=");
			if (paramArr.length == 2) {
				try {
					params.add(paramArr[0], URLDecoder.decode(paramArr[1], CharsetUtil.UTF_8));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			} else if (paramArr.length == 1) {
				params.add(paramArr[0], "");
			}
		}
		return params;
	}


	/**
	 * 将Map参数转换成查询参数
	 *
	 * @return a=1&b=2&c=3
	 */
	public static String convertMapToQuery(Map<String, String> params) {
		List<String> list = new ArrayList<>(params.size());
		try {
			for (Map.Entry<String, ?> entry : params.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value instanceof Collection) {
					Collection collection = (Collection) value;
					for (Object el : collection) {
						list.add(key + "=" + URLEncoder.encode(String.valueOf(el), CharsetUtil.UTF_8));
					}
				} else {
					list.add(key + "=" + URLEncoder.encode(String.valueOf(value), CharsetUtil.UTF_8));
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("不支持的字符集", e);
		}
		return ArrayUtil.join(list.toArray(), StringPool.AMPERSAND);
	}

	/**
	 * 获取请求真实IP
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(ServerHttpRequest request) {
		Map<String, String> headers = request.getHeaders().toSingleValueMap();
		String unknown = "unknown";
		String ip = headers.get("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = headers.get("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = headers.get("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = headers.get("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = headers.get("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = headers.get("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddress().getAddress().getHostAddress();
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 0) {
			String[] ips = ip.split(",");
			if (ips.length > 0) {
				ip = ips[0];
			}
		}
		return ip;
	}
}
