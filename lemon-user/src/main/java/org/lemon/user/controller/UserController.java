package org.lemon.user.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

    @RequestMapping("test")
    public R test() {
        return R.ok("测试成功");
    }
}
