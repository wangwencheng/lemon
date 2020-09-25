package org.lemon.auth.service;

import org.lemon.common.core.util.R;

/**
 * 验证码服务接口
 * @author wwc
 */
public interface ValidateCodeService {

	/**
	 * 发送手机验证码
	 * @param mobile
	 * @param from
	 * @return
	 */
	R<Boolean> sendSmsCode(String mobile, String from);
}
