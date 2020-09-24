//package org.lemon.user.service.impl;
//
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.elib.common.core.constant.enums.ErrorCodeEnum;
//import org.elib.common.core.util.R;
//import org.elib.common.security.util.GbUser;
//import org.elib.user.api.dto.request.BindDTO;
//import org.elib.user.api.entity.UserInfo;
//import org.elib.user.api.enums.ErrorCode;
//import org.elib.user.api.enums.SmsTypeEnum;
//import org.elib.user.mapper.UserMapper;
//import org.elib.user.service.BindService;
//import org.elib.user.service.SmsService;
//import org.elib.user.util.SecurityUserUtil;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service("mobileBind")
//@AllArgsConstructor
//public class MobileBindServiceImpl implements BindService {
//	private final UserMapper userMapper;
//	private final SmsService smsService;
//
//	@Override
//	public R binding(BindDTO request) {
//		if (StrUtil.isBlank(request.getMobile())) {
//			return R.failed(ErrorCode.MOBILE_EMPTY);
//		}
//		R<Boolean> checkCode = smsService.checkCode(SmsTypeEnum.MOBILE_BIND, request.getMobile(), request.getCode(), true);
//		if (checkCode.getCode() != ErrorCodeEnum.SUCCESS.getCode() || !checkCode.getData()) {
//			return R.failed(ErrorCode.MOBILE_BIND_CODE_ERROR);
//		}
//		GbUser gbUser = SecurityUserUtil.getUser();
//		UserInfo mobileUser = userMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getMobile, request.getMobile()));
//		if (null == mobileUser) {
//			UserInfo update = new UserInfo();
//			update.setUserNo(gbUser.getUserNo());
//			update.setMobile(request.getMobile());
//			return update.updateById() ? R.ok("手机绑定成功!") : R.failed("手机绑定失败!");
//		} else {
//			if (mobileUser.getUserNo() != gbUser.getUserNo()) {
//				return R.failed(ErrorCode.MOBILE_ALREADY_BIND);
//			}
//
//			return null;
//		}
//	}
//}
