package org.lemon.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 网关中心
 * @author Wangwch
 */
@SpringCloudApplication
@ComponentScan({"org.lemon.gateway","org.lemon.common.gateway"})
public class GateWayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GateWayApplication.class, args);
	}
}
