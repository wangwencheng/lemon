package org.lemon.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.lemon.common.core.util.R;
import org.lemon.common.security.annotation.Inner;
import org.lemon.user.api.dto.request.SmsCheckDTO;
import org.lemon.user.api.dto.request.SmsSendDTO;
import org.lemon.user.api.enums.ErrorCode;
import org.lemon.user.api.enums.SmsTypeEnum;
import org.lemon.user.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
@Api(value = "sms", tags = "短信模块")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @Inner(false)
    @GetMapping("send")
    @ApiOperation(value = "短信验证码推送", notes = "验证类型{type}详见SmsTypeEnum枚举:1手机注册|2手机绑定|3重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "type", value = "验证类型", required = true, dataType = "Integer", paramType = "body")
    })
    public R send(SmsSendDTO request) {
        return smsService.send(request);
    }

    @Inner(false)
    @GetMapping("check")
    @ApiOperation(value = "短信验证", notes = "验证类型{type}详见SmsTypeEnum枚举:1手机注册|2手机绑定|3重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "Integer", paramType = "body"),
            @ApiImplicitParam(name = "type", value = "验证类型", required = true, dataType = "Integer", paramType = "body")
    })
    public R<Boolean> check(SmsCheckDTO request) {
        SmsTypeEnum smsTypeEnum = SmsTypeEnum.getEnum(request.getType());
        if (null == smsTypeEnum) {
            return R.failed(ErrorCode.MSM_TYPE_ERROR);
        }
        return smsService.checkCode(smsTypeEnum, request.getMobile(), request.getCode(), false);
    }
}
