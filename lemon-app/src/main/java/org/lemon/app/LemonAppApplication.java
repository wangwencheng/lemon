package org.lemon.app;

import org.lemon.common.feign.annotation.EnableLemonFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 认证中心
 * @author Wangwch
 */
@EnableLemonFeignClients
@SpringCloudApplication
public class LemonAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(LemonAppApplication.class, args);
	}
}
