package org.lemon.gateway.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.gateway.result.ResultExecutor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

/**
 * 修改响应内容，合并签名
 *
 * @author wwc
 */
@Slf4j
//@Component
public class ModifyResponseGatewayFilter implements GlobalFilter, Ordered {
	@Autowired
	private ServerCodecConfigurer codecConfigurer;
	@Autowired
	private ResultExecutor resultExecutor;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		return chain.filter(exchange.mutate().response(decorate(exchange)).build());
	}

	@SuppressWarnings("unchecked")
	ServerHttpResponse decorate(ServerWebExchange exchange) {
		return new ServerHttpResponseDecorator(exchange.getResponse()) {

			@Override
			public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

				Class inClass = String.class;
				Class outClass = String.class;

				String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
				if (!StrUtil.containsAnyIgnoreCase(originalResponseContentType, MediaType.APPLICATION_JSON_VALUE)) {
					return super.writeWith(body);
				}
				HttpHeaders httpHeaders = new HttpHeaders();
				// explicitly add it in this way instead of
				// 'httpHeaders.setContentType(originalResponseContentType)'
				// this will prevent exception in case of using non-standard media
				// types like "Content-Type: image"
				httpHeaders.add(HttpHeaders.CONTENT_TYPE, originalResponseContentType);

				ClientResponse clientResponse = prepareClientResponse(body, httpHeaders);

				Mono modifiedBody = clientResponse.bodyToMono(inClass).flatMap(originalBody -> Mono.just(resultExecutor.mergeResult(exchange, String.valueOf(originalBody))));

				BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, outClass);
				CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, exchange.getResponse().getHeaders());
				return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
					Flux<DataBuffer> messageBody = outputMessage.getBody();
					HttpHeaders headers = getDelegate().getHeaders();
					if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
						messageBody = messageBody.doOnNext(data -> headers.setContentLength(data.readableByteCount()));
					}
					// TODO: fail if isStreamingMediaType?
					return getDelegate().writeWith(messageBody);
				}));
			}

			@Override
			public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
				return writeWith(Flux.from(body).flatMapSequential(p -> p));
			}

			private ClientResponse prepareClientResponse(Publisher<? extends DataBuffer> body, HttpHeaders httpHeaders) {
				ClientResponse.Builder builder;
				if (codecConfigurer != null) {
					builder = ClientResponse.create(Objects.requireNonNull(exchange.getResponse().getStatusCode()), codecConfigurer.getReaders());
				} else {
					builder = ClientResponse.create(Objects.requireNonNull(exchange.getResponse().getStatusCode()));
				}
				return builder.headers(headers -> headers.putAll(httpHeaders)).body(Flux.from(body)).build();
			}

		};


	}

	@Override
	public int getOrder() {
		return -2;
	}

}
