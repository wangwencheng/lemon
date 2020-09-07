package org.lemon.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 认证中心
 * @author Wangwch
 */
@SpringCloudApplication
public class LemonAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(LemonAppApplication.class, args);
	}
}
