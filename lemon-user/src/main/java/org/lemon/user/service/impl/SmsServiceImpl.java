package org.lemon.user.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.constant.SecurityConstant;
import org.lemon.common.core.constant.enums.ErrorCodeEnum;
import org.lemon.common.core.util.R;
import org.lemon.user.api.dto.request.SmsSendDTO;
import org.lemon.user.api.entity.UserInfo;
import org.lemon.user.api.enums.ErrorCode;
import org.lemon.user.api.enums.SmsTypeEnum;
import org.lemon.user.api.feign.RemoteUserService;
import org.lemon.user.constant.UserConstant;
import org.lemon.user.service.SmsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class SmsServiceImpl implements SmsService {
    private static final int SMS_CODE_EXPIRE = 5;
    private static final int SMS_CODE_INTERVAL = 120;
    private final RedisTemplate redisTemplate;
    // private final RemoteSmsService remoteSmsService;
    private final RemoteUserService remoteUserService;

    @Override
    public R send(SmsSendDTO request) {
        SmsTypeEnum smsTypeEnum = SmsTypeEnum.getEnum(request.getType());
        if (null == smsTypeEnum) {
            return R.failed(ErrorCode.MSM_TYPE_ERROR);
        }
        String mobile = request.getMobile();
        if (StrUtil.isBlank(mobile)) {
            return R.failed(ErrorCode.MOBILE_EMPTY);
        }
        if (!Validator.isMobile(mobile)) {
            return R.failed(ErrorCode.MOBILE_ERROR);
        }
        R<UserInfo> findMobile = remoteUserService.getByMobile(mobile, SecurityConstant.FROM_IN);
        if (findMobile.getCode() != ErrorCodeEnum.SUCCESS.getCode()) {
            return findMobile;
        }
        // 校验手机号是否有注册
        switch (smsTypeEnum) {
            case MOBILE_REGISTER:
                if (null != findMobile.getData()) {
                    return R.failed(ErrorCode.MOBILE_EXIST);
                }
                break;
            case MOBILE_BIND:
            case RE_PASSWORD:
                if (null == findMobile.getData()) {
                    return R.failed(ErrorCode.MOBILE_NOT_REGISTER);
                }
                break;
        }
        String key = UserConstant.smsRedis(smsTypeEnum, mobile);
        String interval = UserConstant.smsRedisInterval(key);
        if (redisTemplate.hasKey(interval)) {
            return R.failed(ErrorCode.MSM_TOO_FAST);
        }
        Object codeObj = redisTemplate.opsForValue().get(key);
        String code;
        if (codeObj != null) {
            log.debug("手机号{}验证码{}未过期!", mobile, codeObj);
            code = codeObj.toString();
        } else {
            code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstant.CODE_SIZE));
            log.debug("手机号{}成功生成验证码:{}", mobile, code);
            redisTemplate.opsForValue().set(key, code, SMS_CODE_EXPIRE, TimeUnit.MINUTES);
        }
        redisTemplate.opsForValue().setIfAbsent(interval, Boolean.TRUE, SMS_CODE_INTERVAL, TimeUnit.SECONDS);
        // todo 后期改造
//        return remoteSmsService.sentSms(SecurityConstant.FROM_IN, mobile, smsTypeEnum.getTemplateType(), code);
        return R.ok();
    }

    @Override
    public R<Boolean> checkCode(SmsTypeEnum smsTypeEnum, String mobile, String code, boolean removeCode) {
        String key = UserConstant.smsRedis(smsTypeEnum, mobile);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        if (!redisTemplate.hasKey(key)) {
            return R.failed(ErrorCode.REGISTER_MSM_CODE_ERROR);
        }
        Object codeObj = redisTemplate.opsForValue().get(key);
        if (codeObj == null) {
            return R.failed(ErrorCode.REGISTER_MSM_CODE_ERROR);
        }
        String saveCode = codeObj.toString();
        if (StrUtil.isBlank(saveCode) || !StrUtil.equals(saveCode, code)) {
            return R.failed(ErrorCode.REGISTER_MSM_CODE_ERROR);
        }
        if (removeCode) {
            redisTemplate.delete(key);
        }
        return R.ok(Boolean.TRUE);
    }
}
