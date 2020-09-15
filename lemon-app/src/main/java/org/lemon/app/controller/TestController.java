package org.lemon.app.test.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ErvinWang
 */
@RestController
@RequestMapping("test")
public class TestController {
    @RequestMapping("test")
    public R test() {
        return R.ok("测试成功");
    }
}
