package org.lemon.user.constant;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.lemon.common.core.constant.CacheConstant;
import org.lemon.common.core.constant.SecurityConstant;
import org.lemon.user.api.enums.SmsTypeEnum;

public class UserConstant {
	public static String smsRedis(SmsTypeEnum smsTypeEnum, String moblie) {
		return new StringBuilder()
				.append(CacheConstant.SMS_CODE_KEY)
				.append(smsTypeEnum.getCode().toUpperCase()).append(StringPool.COLON)
				.append(moblie).toString();
	}

	public static String smsRedisInterval(String key) {
		return new StringBuilder()
				.append(key)
				.append(StringPool.AT)
				.append(SecurityConstant.CODE_INTERVAL_NAME).toString();
	}
}
