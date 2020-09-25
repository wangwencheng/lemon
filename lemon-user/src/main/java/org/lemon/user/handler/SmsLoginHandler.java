package org.lemon.user.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lemon.user.api.entity.UserInfo;
import org.lemon.user.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 手机验证码登录
 *
 * @author wwc
 */
@Slf4j
@Component("SMS")
@AllArgsConstructor
public class SmsLoginHandler extends AbstractLoginHandler {
    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 验证码登录传入为手机号
     *
     * @param mobile
     * @return
     */
    @Override
    public String identify(String mobile) {
        return mobile;
    }

    /**
     * 通过mobile获取用户信息
     *
     * @param identify
     * @return
     */
    @Override
    public UserInfo info(String identify) {
        UserInfo user = userInfoService.getOne(Wrappers.<UserInfo>query().lambda().eq(UserInfo::getMobile, identify));
        if (user == null) {
            log.info("手机号未注册:{}", identify);
            return null;
        }
        return user;
    }
}
