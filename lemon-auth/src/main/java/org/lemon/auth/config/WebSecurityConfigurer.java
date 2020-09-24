package org.lemon.auth.config;

import lombok.SneakyThrows;
import org.lemon.auth.handler.LemonAuthenticationFailureHandler;
import org.lemon.auth.handler.LemonAuthenticationSuccessHandler;
import org.lemon.common.security.component.LemonPasswordEncoder;
import org.lemon.common.security.handler.MobileLoginSuccessHandler;
import org.lemon.common.security.mobile.MobileSecurityConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 认证相关配置
 *
 * @author Wangwch
 */
@Primary
@Order(90)
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private LemonAuthenticationSuccessHandler lemonAuthenticationSuccessHandler;

    @Autowired
    private LemonAuthenticationFailureHandler lemonAuthenticationFailureHandler;

    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http
                .formLogin()
                .loginPage("/pages/login.html")
                .loginProcessingUrl("/authentication/form")
                .defaultSuccessUrl("/pages/index.html")
                .successHandler(lemonAuthenticationSuccessHandler)//配置successHandler
                .failureHandler(lemonAuthenticationFailureHandler)//配置failureHandler
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/sso/**",
                        "/actuator/**",
                        "/code/**",
                        "/token/**",
                        "/pages/login.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .apply(mobileSecurityConfigurer());
    }


    @Bean
    public AuthenticationSuccessHandler mobileLoginSuccessHandler() {
        return new MobileLoginSuccessHandler();
    }

    @Bean
    public MobileSecurityConfigurer mobileSecurityConfigurer() {
        return new MobileSecurityConfigurer();
    }

    /**
     * 不拦截静态资源
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/pages/**","/assets/**", "/css/**", "/images/**", "/js/**");
    }

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }


    /**
     * https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-storage-updated
     * Encoded password does not look like BCrypt
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new LemonPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .withUser("simm").password("123").roles("USER").and()
//                .withUser("admin").password("admin").roles("USER","ADMIN");
//    }
}