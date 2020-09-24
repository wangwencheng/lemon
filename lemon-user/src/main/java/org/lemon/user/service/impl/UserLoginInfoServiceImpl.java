//package org.lemon.user.service.impl;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.util.StrUtil;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.elib.common.core.constant.SecurityConstant;
//import org.elib.common.core.service.impl.BaseServiceImpl;
//import org.elib.log.api.entity.LogLogin;
//import org.elib.log.api.feign.RemoteLogService;
//import org.elib.message.api.push.feign.RemoteUserPushService;
//import org.elib.user.api.dto.request.LoginDTO;
//import org.elib.user.api.entity.UserInfo;
//import org.elib.user.api.entity.UserLoginInfo;
//import org.elib.user.mapper.UserLoginInfoMapper;
//import org.elib.user.service.UserLoginInfoService;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Slf4j
//@Service
//@AllArgsConstructor
//public class UserLoginInfoServiceImpl extends BaseServiceImpl<UserLoginInfoMapper, UserLoginInfo> implements UserLoginInfoService {
//    private final RemoteUserPushService userPushService;
//	private final RemoteLogService remoteLogService;
//    private final UserLoginInfoMapper userLoginInfoMapper;
//
//	@Async
//	@Override
//	public void login(LoginDTO dto) {
//		if (null == dto.getUserNo()) {
//			log.error("单设备登陆获取用户编号失败,对象:{}", dto);
//			return;
//		}
//		// 删除用户登陆信息
//		if (StrUtil.isNotBlank(dto.getDeviceId())) {
//			userLoginInfoMapper.deleteByMobileTerminal(dto.getUserNo());
//			// 发送消息踢人
//			userPushService.otherDervcieLogin(dto.getUserNo(), dto.getDeviceId(), SecurityConstant.FROM_IN);
//		} else {
//			userLoginInfoMapper.deleteByPcTerminal(dto.getUserNo());
//		}
//		// 新增用户登陆信息
//		if (!dto.insert()) {
//			log.error("用户登陆信息保存失败!失败对象:{}", dto);
//		}
//		// 用户登陆信息表处理
//		UserInfo model = new UserInfo();
//		model.setUserNo(dto.getUserNo());
//		Date lastLoginTime = new Date();
//		model.setLastLoginTime(lastLoginTime);
//		if (!model.updateById()) {
//			log.error("用户信息表最后登陆时间更新失败,失败userNo:{},时间:{}", dto.getUserNo(), lastLoginTime);
//		}
//		// 保存登陆日志
//		LogLogin logLogin = new LogLogin();
//		BeanUtil.copyProperties(dto, logLogin);
//		remoteLogService.saveLogLogin(logLogin, SecurityConstant.FROM_IN);
//	}
//}
