package org.lemon.user.service;

import org.lemon.common.core.util.R;
import org.lemon.user.api.dto.request.SmsSendDTO;
import org.lemon.user.api.enums.SmsTypeEnum;

public interface SmsService {
	R send(SmsSendDTO request);
	R<Boolean> checkCode(SmsTypeEnum smsTypeEnum, String mobile, String code, boolean removeCode);
}
