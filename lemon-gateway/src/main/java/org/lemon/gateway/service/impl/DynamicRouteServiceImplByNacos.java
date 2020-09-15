package org.lemon.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lemon.gateway.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;

import static org.lemon.gateway.constant.Constant.DEFAULT_TIMEOUT;

@Slf4j
@Component
public class DynamicRouteServiceImplByNacos {
    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;
    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    @SneakyThrows
    @PostConstruct
    public void DynamicRouteServiceImplByNacos() {
        log.info("gateway route init...");
        ConfigService configService = NacosFactory.createConfigService(serverAddr);
        if (Objects.isNull(configService)) {
            log.warn("initConfigService fail");
            return;
        }
        String configInfo = configService.getConfig(Constant.dataId, Constant.group, DEFAULT_TIMEOUT);
        log.info("获取到网关配置为:{}", configInfo);
        List<RouteDefinition> list = JSON.parseArray(configInfo, RouteDefinition.class);
        list.forEach(e -> dynamicRouteService.add(e));
        dynamicRouteByNacosListener(Constant.dataId, Constant.group);
    }

    /**
     * 监听 Nacos Server 下发的动态路由配置
     *
     * @param dataId
     * @param group
     */
    public void dynamicRouteByNacosListener(String dataId, String group) {
        try {
            ConfigService configService = NacosFactory.createConfigService(serverAddr);
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    List<RouteDefinition> list = JSON.parseArray(configInfo, RouteDefinition.class);
                    list.forEach(e -> dynamicRouteService.update(e));
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.info("dynamicRouteByNacosListener has occur a Exception", e);
        }
    }

    private ConfigService initConfigService() throws NacosException {
        return NacosFactory.createConfigService(serverAddr);
    }
}
