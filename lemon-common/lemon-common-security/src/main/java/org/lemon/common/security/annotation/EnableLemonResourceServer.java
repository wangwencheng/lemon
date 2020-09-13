package org.lemon.common.security.annotation;

import org.lemon.common.security.component.GbResourceServerAutoConfiguration;
import org.lemon.common.security.component.GbSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * 资源服务注解
 * @author Donald
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({GbResourceServerAutoConfiguration.class, GbSecurityBeanDefinitionRegistrar.class})
public @interface EnableLemonResourceServer {

}
