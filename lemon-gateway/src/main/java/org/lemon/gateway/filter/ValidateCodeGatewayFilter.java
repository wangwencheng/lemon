package org.lemon.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.constant.CacheConstant;
import org.lemon.common.core.constant.SecurityConstant;
import org.lemon.common.core.constant.enums.LoginTypeEnum;
import org.lemon.common.core.util.R;
import org.lemon.common.core.util.WebUtils;
import org.lemon.gateway.config.FilterIgnorePropertiesConfig;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 短信验证码处理
 *
 * @author wwc
 */
@Slf4j
@Component
@AllArgsConstructor
class ValidateCodeGatewayFilter extends AbstractGatewayFilterFactory {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 不是登录请求，直接向下执行
            if (!StrUtil.containsAnyIgnoreCase(request.getURI().getPath()
                    , SecurityConstant.OAUTH_TOKEN_URL, SecurityConstant.SMS_TOKEN_URL
                    , SecurityConstant.SOCIAL_TOKEN_URL)) {
                return chain.filter(exchange);
            }

            // 刷新token，直接向下执行
            String grantType = request.getQueryParams().getFirst("grant_type");
            if (StrUtil.equals(SecurityConstant.REFRESH_TOKEN, grantType)) {
                return chain.filter(exchange);
            }

            // 终端设置不校验， 直接向下执行
            try {
                String[] clientInfos = WebUtils.getClientId(request);
                if (filterIgnorePropertiesConfig.getClients().contains(clientInfos[0])) {
                    return chain.filter(exchange);
                }

                // 如果是社交登录，判断是否包含SMS
                if (StrUtil.containsAnyIgnoreCase(request.getURI().getPath(), SecurityConstant.SMS_TOKEN_URL)) {
                    String type = request.getQueryParams().getFirst("type");
                    if (type.equals(LoginTypeEnum.SMS.getType())) {
                        //校验验证码
                        checkCode(request);
                    } else {
                        return chain.filter(exchange);
                    }
                }
            } catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
                try {
                    return response.writeWith(Mono.just(response.bufferFactory()
                            .wrap(objectMapper.writeValueAsBytes(
                                    R.failed(e.getMessage())))));
                } catch (JsonProcessingException e1) {
                    log.error("对象输出异常", e1);
                }
            }

            return chain.filter(exchange);
        };
    }

    /**
     * 校验验证码
     *
     * @param request
     */
    @SneakyThrows
    private void checkCode(ServerHttpRequest request) {
        String code = request.getQueryParams().getFirst("code");

        if (StrUtil.isBlank(code)) {
            throw new RuntimeException("验证码不能为空");
        }
        String openId = request.getQueryParams().getFirst("openId");

        String key = CacheConstant.SMS_CODE_KEY + "login" + StringPool.COLON + StrUtil.subBefore(openId, StringPool.AT, false) + StringPool.COLON + StrUtil.subAfter(openId, StringPool.AT, false);
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        if (!redisTemplate.hasKey(key)) {
            throw new RuntimeException("验证码不合法");
        }

        Object codeObj = redisTemplate.opsForValue().get(key);

        if (codeObj == null) {
            throw new RuntimeException("验证码不合法");
        }

        String saveCode = codeObj.toString();
        if (StrUtil.isBlank(saveCode)) {
            redisTemplate.delete(key);
            throw new RuntimeException("验证码不合法");
        }

        if (!StrUtil.equals(saveCode, code)) {
            redisTemplate.delete(key);
            throw new RuntimeException("验证码不合法");
        }

        redisTemplate.delete(key);
    }
}
