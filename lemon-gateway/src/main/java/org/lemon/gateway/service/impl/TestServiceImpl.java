package org.lemon.gateway.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.lemon.gateway.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    @SentinelResource(value = "sayHello", blockHandler = "blockHandler", fallback = "fallBack")
    public String sayHello(String name) {
        if (name.equals("1")) {
            throw new ExceptionInInitializerError("出异常了，参数不能是：" + name);
        }
        return "Hello, " + name;
    }

    /**
     * 被限流时调用的方法
     *
     * @param str 被限流的方法的参数
     * @param ex  限流异常
     * @return String
     */
    public String blockHandler(String str, BlockException ex) {
        return "被限流了。。。" + str;
    }


    /**
     * 处理java代码中的异常，不管有没有达到Sentinel中的配置<br>
     * 只要java代码中出现异常就会调用，如果断流的方法被调用，则这个方法不被调用
     *
     * @param name 被控制的方法的参数
     * @param e 代码中抛出的异常，这个参数是可选的
     * @return
     */
    public String fallBack(String name, Throwable e) {
        System.err.println("Java代码有异常："+e.getMessage());
        return "业务方法中出现异常: " + name + " -> " + e.getMessage();
    }

}
