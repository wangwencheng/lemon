package org.lemon.user.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.AllArgsConstructor;
import org.lemon.common.core.service.impl.BaseServiceImpl;
import org.lemon.user.api.entity.UserInfo;
import org.lemon.user.handler.LoginHandler;
import org.lemon.user.mapper.UserInfoMapper;
import org.lemon.user.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户服务实现
 *
 * @author wwc
 */
@Service
public class SocialServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo> implements SocialService {
	@Autowired
	private  Map<String, LoginHandler> loginHandlerMap;

	/**
	 * 通过手机号、第三方标识获取用户信息
	 *
	 * @param openId
	 * @return
	 */
	@Override
	public UserInfo getUserInfo(String openId) {
		String type = StrUtil.subBefore(openId, StringPool.AT, false);
		String loginStr = StrUtil.subAfter(openId, StringPool.AT, false);
		return loginHandlerMap.get(type).handle(loginStr);
	}
}
