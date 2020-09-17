package org.lemon.user;

import org.lemon.common.feign.annotation.EnableLemonFeignClients;
import org.lemon.common.security.annotation.EnableLemonResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 认证中心
 * @author Wangwch
 */


@SpringCloudApplication
@EnableLemonFeignClients
//@EnableLemonResourceServer
public class UserApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
