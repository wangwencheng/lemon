package org.lemon.user;

import org.lemon.common.feign.annotation.EnableLemonFeignClients;
import org.lemon.common.security.annotation.EnableLemonResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 认证中心
 * @author Wangwch
 */


@EnableDiscoveryClient
@SpringBootApplication
@EnableLemonFeignClients
@EnableLemonResourceServer
public class UserApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
