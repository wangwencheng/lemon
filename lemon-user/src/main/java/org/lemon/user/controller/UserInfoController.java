package org.lemon.user.controller;


import org.lemon.common.core.util.R;
import org.lemon.user.api.entity.UserInfo;
import org.lemon.user.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-09-17
 */
@RestController
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping("/mobile/info/{mobile}")
    R<UserInfo> getByMobile(@PathVariable("mobile") String mobile) {
        return R.ok(userInfoService.getByMobile(mobile));
    }

}
