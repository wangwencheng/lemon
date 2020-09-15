package org.lemon.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.util.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ErvinWang
 */
@Slf4j
@RestController
public class UserController {

    @RequestMapping(value = "test")
    public R
    test() {
        log.info("测试成功");
        return R.ok("测试成功");
    }
}
