package org.lemon.app.controller;


import com.baomidou.mybatisplus.extension.api.R;
import org.lemon.app.entity.AppButton;
import org.lemon.app.service.IAppButtonService;
import org.lemon.common.security.annotation.Inner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ervin.Wang
 * @since 2020-10-28
 */
@Inner
@RestController
@RequestMapping("button")
public class AppButtonController {
    @Autowired
    private IAppButtonService appButtonService;

    @GetMapping("list")
    public R getButtonList(AppButton appButton) {
        appButton.setFetchSize(10);
        return R.ok(appButtonService.getButtonList(appButton));
    }

}
