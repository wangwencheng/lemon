package org.lemon.auth;

import org.lemon.common.feign.annotation.EnableLemonFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 认证中心
 *
 * @author Wangwch
 */
@SpringCloudApplication
@EnableLemonFeignClients
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
