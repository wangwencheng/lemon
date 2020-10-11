package org.lemon.gateway.filter;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONTokener;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.gateway.param.ParamBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 重写请求参数
 *
 * @author wwc
 */
//@Component
@Slf4j
public class ModifyParameterGatewayFilter implements Ordered, GlobalFilter {
    @Autowired
    private ParamBuilder paramBuilder;

    @Override
    public Mono filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Map<String, String> params;
        // 重写请求参数
        try {
            params = paramBuilder.build(exchange);
        } catch (Exception e) {
            return Mono.error(e);
        }

        if (exchange.getRequest().getMethod() == HttpMethod.GET) {
            URI uri = exchange.getRequest().getURI();
            String query = HttpUtil.toParams(params);
            try {
                URI newUri = UriComponentsBuilder.fromUri(uri).replaceQuery(query).build(true).toUri();
                ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri).build();
                return chain.filter(exchange.mutate().request(request).build());
            } catch (RuntimeException ex) {
                throw new IllegalStateException("Invalid URI query: \"" + query + "\"");
            }
        }

        if (exchange.getRequest().getMethod() == HttpMethod.POST) {
            MediaType contentType = exchange.getRequest().getHeaders().getContentType();
            if (contentType == null) {
                contentType = MediaType.APPLICATION_FORM_URLENCODED;
            }
            Mono<String> modifiedBody;
            if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
                JSONObject jsonObject = new JSONObject();
                for (String key : params.keySet()) {
                    jsonObject.put(key, params.get(key));
                    try {
                        Object json = new JSONTokener(params.get(key)).nextValue();
                        if (json instanceof JSONArray) {
                            jsonObject.put(key, json);
                        }
                    } catch (Exception ignored) {
                        log.info("修改参数失败", ignored);
                    }
                }
                modifiedBody = Mono.just(jsonObject.toString());
            } else if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType)) {
                modifiedBody = Mono.just(HttpUtil.toParams(params));
            } else {
                return chain.filter(exchange);
            }

            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            headers.set(HttpHeaders.CONTENT_TYPE, contentType.toString());
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
            return bodyInserter.insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        ServerHttpRequest decorator = decorate(exchange, headers, outputMessage);
                        return chain.filter(exchange.mutate().request(decorator).build());
                    }));
        }
        return chain.filter(exchange);
    }

    protected ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers, CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    // TODO: this causes a 'HTTP/1.1 411 Length Required' // on
                    // httpbin.org
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

    @Override
    public int getOrder() {
        return -10;
    }

    private Map<String, Object> decodeBody(String body) {
        return Arrays.stream(body.split("&"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    private String encodeBody(Map<String, Object> map) {
        return map.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
    }
}
