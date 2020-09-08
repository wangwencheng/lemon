//package org.elib.auth.service.impl;
//
//import cn.hutool.core.util.RandomUtil;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.elib.auth.service.ValidateCodeService;
//import org.elib.common.core.constant.CacheConstant;
//import org.elib.common.core.constant.SecurityConstant;
//import org.elib.common.core.constant.enums.SystemTypeEnum;
//import org.elib.common.core.util.R;
//import org.elib.common.data.tenant.TenantContextHolder;
//import org.elib.message.api.sms.feign.RemoteSmsService;
//import org.elib.user.api.entity.UserInfo;
//import org.elib.user.api.feign.RemoteUserService;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 验证码服务实现
// *
// * @author Donald
// */
//@Slf4j
//@Service
//@AllArgsConstructor
//public class ValidateCodeServiceImpl implements ValidateCodeService {
//	private final RedisTemplate redisTemplate;
//	private final RemoteUserService remoteUserService;
//	private final RemoteSmsService remoteSmsService;
//
//	/**
//	 * 发送手机验证码
//	 *
//	 * @param mobile
//	 * @param from
//	 * @return
//	 */
//	@Override
//	public R<Boolean> sendSmsCode(String mobile, String from) {
//		if (from.equals(SystemTypeEnum.APP.getType())) {
//			R<UserInfo> result = remoteUserService.getByMobile(mobile, SecurityConstant.FROM_IN);
//			if (result == null || result.getData() == null) {
//				log.info("手机号未注册:{}", mobile);
//				return R.ok(Boolean.FALSE, "手机号未注册");
//			}
//		}
//		String key = TenantContextHolder.getTenantId() + StringPool.COLON + CacheConstant.SMS_CODE_KEY + "login" + StringPool.COLON + from + StringPool.COLON + mobile;
//
//		if (redisTemplate.hasKey(key + StringPool.AT + SecurityConstant.CODE_INTERVAL_NAME)) {
//			log.info("验证码发送过于频繁:{}", mobile);
//			return R.ok(Boolean.FALSE, "验证码发送过于频繁");
//		}
//
//		Object codeObj = redisTemplate.opsForValue().get(key);
//		String code = "";
//		if (codeObj != null) {
//			log.info("手机号验证码未过期:{}，{}", mobile, codeObj);
//			code = codeObj.toString();
//		} else {
//			code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstant.CODE_SIZE));
//			log.debug("手机号生成验证码成功:{},{}", mobile, code);
//			redisTemplate.opsForValue().set(key, code, SecurityConstant.CODE_TIME, TimeUnit.SECONDS);
//		}
//
//		redisTemplate.opsForValue().setIfAbsent(key + StringPool.AT + SecurityConstant.CODE_INTERVAL_NAME, Boolean.TRUE, SecurityConstant.CODE_INTERVAL, TimeUnit.SECONDS);
//		remoteSmsService.sentSms(SecurityConstant.FROM_IN, mobile, "Login", code);
//		return R.ok(Boolean.TRUE, "验证码发送成功");
//	}
//}
