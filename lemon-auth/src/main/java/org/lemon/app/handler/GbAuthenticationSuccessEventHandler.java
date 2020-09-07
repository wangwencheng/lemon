//package org.elib.auth.handler;
//
//import cn.hutool.core.util.StrUtil;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.elib.common.core.constant.SecurityConstant;
//import org.elib.common.core.constant.enums.SystemTypeEnum;
//import org.elib.common.core.util.WebUtils;
//import org.elib.common.data.tenant.TenantContextHolder;
//import org.elib.common.security.component.GbAuthenticationKeyGenerator;
//import org.elib.common.security.handler.AbstractAuthenticationSuccessEventHandler;
//import org.elib.common.security.util.AuthUtil;
//import org.elib.common.security.util.GbUser;
//import org.elib.user.api.dto.request.LoginDTO;
//import org.elib.user.api.feign.RemoteUserService;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2RefreshToken;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//
///**
// * @author Donald
// */
//@Slf4j
//@Component
//@AllArgsConstructor
//public class GbAuthenticationSuccessEventHandler extends AbstractAuthenticationSuccessEventHandler {
//	private static final String ELIB_OAUTH_ACCESS = SecurityConstant.ELIB_PREFIX + SecurityConstant.OAUTH_PREFIX + "auth_to_access:";
//	private final RedisTemplate redisTemplate;
//	private final TokenStore tokenStore;
//	private final RemoteUserService remoteUserService;
//
//	/**
//	 * 处理登录成功方法
//	 * <p>
//	 * 获取到登录的authentication 对象
//	 *
//	 * @param authentication 登录对象
//	 * @param request        请求
//	 * @param response       返回
//	 */
//	@Override
//	public void handle(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
//		String username = request.getParameter(SecurityConstant.DETAILS_USERNAME);
//		if (username.indexOf(SystemTypeEnum.OMS.getType()) != 0) {
//			userLogin(authentication, request);
//		}
//		log.info("用户：{} 登录成功", authentication.getPrincipal());
//	}
//
//	private void userLogin(Authentication authentication, HttpServletRequest request) {
//		String loginOsType = request.getHeader(SecurityConstant.LOGIN_OS_TYPE_PARAMETER);
//		String loginType = request.getHeader(SecurityConstant.LOGIN_TYPE_PARAMETER);
//		String deviceId = request.getHeader(SecurityConstant.DEVICE_ID_PARAMETER);
//		// 清除当前用户其它设备token
//		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//		String[] tokens = AuthUtil.extractAndDecodeHeader(header);
//		assert tokens.length == 2;
//		String clientId = tokens[0];
//		String key = ELIB_OAUTH_ACCESS + TenantContextHolder.getTenantId() + StrUtil.COLON + new GbAuthenticationKeyGenerator().extractKey(authentication, clientId);
//		List<String> clientOnlyList = StrUtil.splitTrim(SecurityConstant.DEVICE_ONLY_CLIENT, StrUtil.COMMA);
//		LoginDTO loginDTO = new LoginDTO();
//		if (StrUtil.isNotBlank(deviceId) && clientOnlyList.contains(clientId)) {
//			redisTemplate.setKeySerializer(new StringRedisSerializer());
//			redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//			Set<String> keySet = redisTemplate.keys(key + "*");
//			List<String> deviceIds = new ArrayList<>();
//			Iterator<String> it = keySet.iterator();
//			while (it.hasNext()) {
//				String keyStr = it.next();
//				if (!keyStr.equals(key + StrUtil.COLON + deviceId)) {
//					OAuth2AccessToken accessToken = (OAuth2AccessToken) redisTemplate.opsForValue().get(keyStr);
//					// 清空access token
//					tokenStore.removeAccessToken(accessToken);
//					// 清空 refresh token
//					OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
//					tokenStore.removeRefreshToken(refreshToken);
//					// 获取设备号
//					deviceIds.add(StrUtil.subAfter(keyStr, StrUtil.COLON, false));
//				}
//			}
//			loginDTO.setPreDeviceIds(deviceIds);
//		}
//		//登录日志
//		Object principal = authentication.getPrincipal();
//		if (principal instanceof GbUser) {
//			GbUser gbUser = (GbUser) principal;
//			loginDTO.setUserNo(gbUser.getUserNo())
//					.setLoginOsType(StrUtil.isBlank(loginOsType) ? Integer.valueOf(0) : Integer.valueOf(loginOsType))
//					.setLoginIp(WebUtils.getIP(request))
//					.setLoginType(StrUtil.isBlank(loginType) ? Integer.valueOf(0) : Integer.valueOf(loginType))
//					.setDeviceId(deviceId)
//					.setDeviceFactory(request.getHeader(SecurityConstant.DEVICE_FACTORY_PARAMETER))
//					.setDeviceMode(request.getHeader(SecurityConstant.DEVICE_MODE_PARAMETER))
//					.setAppVersionName(request.getHeader(SecurityConstant.APP_VERSION_NAME_PARAMETER))
//					.setAppVersionNo(request.getHeader(SecurityConstant.APP_VERSION_NO_PARAMETER))
//					.setCreateBy(gbUser.getUserNo());
//			remoteUserService.login(loginDTO, SecurityConstant.FROM_IN);
//		}
//	}
//}
//
