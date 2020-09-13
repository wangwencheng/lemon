package org.lemon.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.lemon.gateway.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class DynamicRouteServiceImplByNacos {
    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;
    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    @PostConstruct
    public void DynamicRouteServiceImplByNacos() {
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
            String content = configService.getConfig(dataId, group, 5000);
            log.info(content);
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    RouteDefinition definition = JSON.parseObject(configInfo, RouteDefinition.class);
                    dynamicRouteService.update(definition);
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
}
