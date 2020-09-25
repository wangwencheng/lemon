package org.lemon.auth.controller;

import lombok.AllArgsConstructor;
import org.lemon.auth.service.ValidateCodeService;
import org.lemon.common.core.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码模块
 *
 * @author wwc
 */
@RestController
@AllArgsConstructor
@RequestMapping("/code")
public class ValidateCodeController {
	private final ValidateCodeService validateCodeService;

	/**
	 * 发送手机验证码
	 * @param mobile
	 * @param from
	 * @return
	 */
	@GetMapping("/sms")
	public R sendSmsCode(@RequestParam String mobile, @RequestParam String from) {
		return validateCodeService.sendSmsCode(mobile, from);
	}

}
