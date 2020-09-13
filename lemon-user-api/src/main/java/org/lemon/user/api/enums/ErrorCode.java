package org.lemon.user.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lemon.common.core.util.IErrorCode;

@Getter
@AllArgsConstructor
public enum ErrorCode implements IErrorCode {
	TOKEN_ERROR(60401, "token无效!"),
	USER_NOT_FIND(60404, "未找到用户!"),
	MOBILE_ERROR(60000, "请输入正确的手机号码!"),
	MOBILE_EXIST(60001, "手机号码已存在!"),
	NICKNAME_LENGTH(60002, "昵称最大11个字!"),
	SEX_ERROR(60003, "性别有误!"),
	MOBILE_NOT_REGISTER(60004, "该手机号未注册，请先注册!"),
	MOBILE_EMPTY(60005, "手机号不能为空!"),
	MOBILE_ALREADY_BIND(60006, "当前手机已被绑定!"),
	PASSWORD_EMPTY(60010, "密码不能为空!"),
	PASSWORD_ERROR(60011, "原密码不正确!"),
	PASSWORD_REGEX_ERROR(60012, "密码为6-16位字母和数字的组合!"),
	EMAIL_ERROR(60020, "邮箱格式有误!"),
	MSM_TYPE_ERROR(60030, "验证类型有误!"),
	REGISTER_MSM_CODE_ERROR(60031, "验证码有误!"), // 手机注册验证码有误
	MSM_TOO_FAST(60032, "验证码发送过于频繁!"),
	MOBILE_BIND_CODE_ERROR(60033, "验证码有误!"),    // 手机绑定验证码有误
	;
	private int code;
	private String msg;
}
