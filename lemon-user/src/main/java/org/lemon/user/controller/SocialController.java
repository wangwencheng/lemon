package org.lemon.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.lemon.common.core.controller.BaseController;
import org.lemon.common.core.util.R;
import org.lemon.common.security.annotation.Inner;
import org.lemon.user.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 社交登录模块
 *
 * @author wwc
 */
@RestController
@RequestMapping("/social")
@Api(value = "social", tags = "社交登录模块")
public class SocialController extends BaseController {
    @Autowired
    private SocialService socialService;

    /**
     * 通过手机号、第三方标识获取用户信息
     *
     * @param openId
     * @return
     */
    @Inner
    @GetMapping("/info/{openId}")
    @ApiOperation(value = "根据用户名查询用户（内部）", notes = "根据用户名查询用户")
    @ApiImplicitParam(name = "openId", value = "用户标识(type@openid)", required = true, dataType = "String", paramType = "path")
    public R getUserInfo(@PathVariable String openId) {
        return R.ok(socialService.getUserInfo(openId));
    }

}
