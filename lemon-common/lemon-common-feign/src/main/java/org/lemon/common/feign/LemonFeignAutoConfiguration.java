package org.lemon.common.feign;

import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.LemonFeignClientsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * feign 自动化配置
 * @author Donald
 */
@Configuration
@ConditionalOnClass(Feign.class)
@Import(LemonFeignClientsRegistrar.class)
@AutoConfigureAfter(EnableFeignClients.class)
public class LemonFeignAutoConfiguration {

}
