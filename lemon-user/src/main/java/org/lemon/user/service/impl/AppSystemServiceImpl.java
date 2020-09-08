//package org.elib.auth.service.impl;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.util.StrUtil;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.elib.auth.dto.request.RestPwd;
//import org.elib.auth.service.SystemCommonService;
//import org.elib.common.core.constant.SecurityConstant;
//import org.elib.common.core.constant.enums.ErrorCodeEnum;
//import org.elib.common.core.constant.enums.SystemTypeEnum;
//import org.elib.common.core.util.R;
//import org.elib.common.data.tenant.TenantContextHolder;
//import org.elib.user.api.dto.request.RestPwdDTO;
//import org.elib.user.api.feign.RemoteUserService;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2RefreshToken;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service("app")
//@AllArgsConstructor
//public class AppSystemServiceImpl implements SystemCommonService {
//	private static final String PREVIOUS_USER_TOKEN = "previous:user:token:";
//	private static final String COLON = ":";
//	private final TokenStore tokenStore;
//	private final RedisTemplate redisTemplate;
//	private final RemoteUserService remoteUserService;
//
//	@Override
//	public R resetPassword(RestPwd request) {
//		R<Long> result = remoteUserService.resetPassword(BeanUtil.toBean(request, RestPwdDTO.class), SecurityConstant.FROM_IN);
//		if (result.getCode() != ErrorCodeEnum.SUCCESS.getCode()) {
//			return result;
//		}
//		Long userNo = result.getData();
//		// 清除移动端token
//		clearToken(SystemTypeEnum.APP, userNo);
//		// 清除PC端token
//		clearToken(SystemTypeEnum.PC, userNo);
//		return R.ok("重置密码成功!");
//	}
//
//	/**
//	 * 清除token
//	 */
//	private void clearToken(SystemTypeEnum systemTypeEnum, Long userNo) {
//		String tokenKey = TenantContextHolder.getTenantId() + COLON + PREVIOUS_USER_TOKEN + systemTypeEnum.getType() + COLON + userNo;
//		String tokenValue = (String) redisTemplate.opsForValue().get(tokenKey);
//		if (StrUtil.isBlank(tokenValue)) {
//			return;
//		}
//		OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
//		if (null == accessToken || StrUtil.isNotBlank(accessToken.getValue())) {
//			return;
//		}
//		tokenStore.removeAccessToken(accessToken);
//		OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
//		tokenStore.removeRefreshToken(refreshToken);
//	}
//}
