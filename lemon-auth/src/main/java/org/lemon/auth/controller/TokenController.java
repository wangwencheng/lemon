//package org.elib.auth.controller;
//
//import cn.hutool.core.map .MapUtil;
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.elib.common.core.constant.CacheConstant;
//import org.elib.common.core.constant.PaginationConstant;
//import org.elib.common.core.constant.SecurityConstant;
//import org.elib.common.core.constant.enums.SystemTypeEnum;
//import org.elib.common.core.util.R;
//import org.elib.common.data.tenant.TenantContextHolder;
//import org.elib.common.security.annotation.Inner;
//import org.elib.common.security.util.GbUser;
//import org.elib.message.api.push.feign.RemoteUserPushService;
//import org.springframework.cache.CacheManager;
//import org.springframework.data.redis.core.ConvertingCursor;
//import org.springframework.data.redis.core.Cursor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ScanOptions;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2RefreshToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Token模块
// *
// * @author Donald
// */
//@Slf4j
//@RestController
//@AllArgsConstructor
//@RequestMapping("/token")
//public class TokenController {
//	private static final String ELIB_OAUTH_ACCESS = SecurityConstant.ELIB_PREFIX + SecurityConstant.OAUTH_PREFIX + "auth_to_access";
//	private final RedisTemplate redisTemplate;
//	private final TokenStore tokenStore;
//	private final CacheManager cacheManager;
//	private final RemoteUserPushService userPushService;
//
//	@GetMapping("/login")
//	public R login(HttpServletRequest request) {
//		return R.failed("不支持的登录方式");
//	}
//
//	/**
//	 * 退出token
//	 *
//	 * @param authHeader Authorization
//	 */
//	@DeleteMapping("/logout")
//	public R logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader, @RequestParam String from) {
//		if (StrUtil.isBlank(authHeader)) {
//			return R.ok(Boolean.FALSE, "退出失败，token 为空");
//		}
//		String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
//		return delToken(tokenValue, from);
//	}
//
//	/**
//	 * 令牌管理调用
//	 *
//	 * @param token token
//	 * @return
//	 */
//	@Inner
//	@DeleteMapping("/{token}")
//	public R<Boolean> delToken(@PathVariable("token") String token, @RequestParam String from) {
//		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
//		if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
//			return R.ok(Boolean.TRUE, "退出失败，token 无效");
//		}
//
//		OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(accessToken);
//		SystemTypeEnum type = SystemTypeEnum.valueOf(from.toUpperCase());
//		// 清空用户信息
//		switch (type) {
//			case APP:
//				if (auth2Authentication.getPrincipal() instanceof GbUser) {
//					userPushService.otherDervcieLoginOut(((GbUser) auth2Authentication.getPrincipal()).getUserNo(), SecurityConstant.FROM_IN);
//				}
//				break;
//			case PC:
//				cacheManager.getCache(CacheConstant.USER_DETAILS)
//						.evict(StrUtil.subAfter(auth2Authentication.getName(), StringPool.AT, false));
//				break;
//			case OMS:
//				cacheManager.getCache(CacheConstant.OPR_DETAILS)
//						.evict(StrUtil.subAfter(auth2Authentication.getName(), StringPool.AT, false));
//				break;
//			default:
//				break;
//		}
//
//		// 清空access token
//		tokenStore.removeAccessToken(accessToken);
//
//		// 清空 refresh token
//		OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
//		tokenStore.removeRefreshToken(refreshToken);
//		return R.ok();
//	}
//
//	/**
//	 * 查询token
//	 *
//	 * @param params 分页参数
//	 * @return
//	 */
//	@Inner
//	@PostMapping("/page")
//	public R<Page> tokenList(@RequestBody Map<String, Object> params) {
//		//根据分页参数获取对应数据
//		String key = String.format("%s:%s*", ELIB_OAUTH_ACCESS, TenantContextHolder.getTenantId());
//		List<String> pages = findKeysForPage(key, MapUtil.getInt(params, PaginationConstant.CURRENT)
//				, MapUtil.getInt(params, PaginationConstant.SIZE));
//
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//		Page result = new Page(MapUtil.getInt(params, PaginationConstant.CURRENT), MapUtil.getInt(params, PaginationConstant.SIZE));
//		result.setRecords(redisTemplate.opsForValue().multiGet(pages));
//		result.setTotal((long) redisTemplate.keys(key).size());
//		return R.ok(result);
//	}
//
//
//	private List<String> findKeysForPage(String patternKey, int pageNum, int pageSize) {
//		ScanOptions options = ScanOptions.scanOptions().count(1000L)
//				.match(patternKey).build();
//		RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
//		Cursor cursor = (Cursor) redisTemplate.executeWithStickyConnection(redisConnection -> new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize));
//		List<String> result = new ArrayList<>();
//		int tmpIndex = 0;
//		int startIndex = (pageNum - 1) * pageSize;
//		int end = pageNum * pageSize;
//
//		assert cursor != null;
//		while (cursor.hasNext()) {
//			if (tmpIndex >= startIndex && tmpIndex < end) {
//				result.add(cursor.next().toString());
//				tmpIndex++;
//				continue;
//			}
//			if (tmpIndex >= end) {
//				break;
//			}
//			tmpIndex++;
//			cursor.next();
//		}
//
//		try {
//			cursor.close();
//		} catch (IOException e) {
//			log.error("关闭cursor 失败");
//		}
//		return result;
//	}
//}
