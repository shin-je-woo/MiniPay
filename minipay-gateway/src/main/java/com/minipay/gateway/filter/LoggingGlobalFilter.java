package com.minipay.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@RequiredArgsConstructor
public class LoggingGlobalFilter implements GlobalFilter {

    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final String EMPTY_BODY_PLACEHOLDER = "<no body>";
    private static final String REQUEST_LOG_PREFIX = "[REQUEST] :: {}";
    private static final String RESPONSE_LOG_PREFIX = "[RESPONSE] :: {}";
    private static final String ERROR_SERIALIZING_REQUEST = "Error serializing request log";
    private static final String ERROR_SERIALIZING_RESPONSE = "Error serializing response log";

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        var response = exchange.getResponse();
        var bufferFactory = response.bufferFactory();

        // 요청 ID 확인 또는 생성
        String requestId = getRequestId(request).orElseGet(this::createRequestId);

        // 요청에 본문이 있는지 확인
        if (!hasRequestBody(request.getHeaders())) {
            logRequest(requestId, request.getMethod(), request.getURI(), request.getHeaders(), EMPTY_BODY_PLACEHOLDER);
            return chain.filter(exchange.mutate()
                    .request(request.mutate().header(REQUEST_ID_HEADER, requestId).build())
                    .build());
        }

        // 요청 본문이 있는 경우 처리
        return processRequestWithBody(exchange, chain, requestId, bufferFactory);
    }

    private Optional<String> getRequestId(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().getFirst(REQUEST_ID_HEADER));
    }

    private String createRequestId() {
        return UUID.randomUUID().toString();
    }

    private boolean hasRequestBody(HttpHeaders headers) {
        return headers.getContentLength() > 0 ||
                (headers.getContentType() != null &&
                        headers.getContentType().toString().toLowerCase().contains("json"));
    }

    private Mono<Void> processRequestWithBody(
            ServerWebExchange exchange,
            GatewayFilterChain chain,
            String requestId,
            org.springframework.core.io.buffer.DataBufferFactory bufferFactory) {

        var request = exchange.getRequest();
        var response = exchange.getResponse();

        return DataBufferUtils.join(request.getBody())
                .defaultIfEmpty(bufferFactory.wrap(new byte[0]))
                .flatMap(dataBuffer -> {
                    // 요청 본문 읽기
                    byte[] requestBodyBytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(requestBodyBytes);
                    DataBufferUtils.release(dataBuffer);

                    // 요청 본문 로깅
                    String requestBodyString = new String(requestBodyBytes, StandardCharsets.UTF_8);
                    String bodyToLog = requestBodyString.isEmpty() ? EMPTY_BODY_PLACEHOLDER : requestBodyString;
                    logRequest(requestId, request.getMethod(), request.getURI(), request.getHeaders(), bodyToLog);

                    // 요청 및 응답 데코레이터 생성
                    var decoratedRequest = createRequestDecorator(
                            request.mutate().header(REQUEST_ID_HEADER, requestId).build(),
                            bufferFactory,
                            requestBodyBytes);

                    var decoratedResponse = createResponseDecorator(response, bufferFactory, requestId);
                    return chain.filter(
                            exchange.mutate()
                                    .request(decoratedRequest)
                                    .response(decoratedResponse)
                                    .build()
                    );
                });
    }

    private ServerHttpRequestDecorator createRequestDecorator(
            org.springframework.http.server.reactive.ServerHttpRequest request,
            org.springframework.core.io.buffer.DataBufferFactory bufferFactory,
            byte[] bodyBytes) {

        return new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
                return Flux.defer(() -> Flux.just(bufferFactory.wrap(bodyBytes)));
            }
        };
    }

    private ServerHttpResponseDecorator createResponseDecorator(
            org.springframework.http.server.reactive.ServerHttpResponse response,
            org.springframework.core.io.buffer.DataBufferFactory bufferFactory,
            String requestId) {

        return new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<DataBuffer> fluxBody = (Flux<DataBuffer>) body;
                    return super.writeWith(fluxBody.flatMap(buffer -> {
                        byte[] responseBytes = new byte[buffer.readableByteCount()];
                        buffer.read(responseBytes);
                        DataBufferUtils.release(buffer);

                        String responseBody = new String(responseBytes, StandardCharsets.UTF_8);
                        String bodyToLog = responseBody.isEmpty() ? EMPTY_BODY_PLACEHOLDER : responseBody;
                        logResponse(requestId, bodyToLog);

                        return Mono.just(bufferFactory.wrap(responseBytes));
                    }));
                }
                return super.writeWith(body);
            }
        };
    }

    private void logRequest(String requestId, HttpMethod method, URI uri, HttpHeaders headers, String body) {
        Map<String, Object> requestLog = new HashMap<>();
        requestLog.put("requestId", requestId);
        requestLog.put("method", method.name());
        requestLog.put("uri", uri);
        requestLog.put("headers", headers.toSingleValueMap());
        requestLog.put("body", parseJsonBody(body));

        logAsJson(requestLog, REQUEST_LOG_PREFIX, ERROR_SERIALIZING_REQUEST);
    }

    private void logResponse(String requestId, String body) {
        Map<String, Object> responseLog = new HashMap<>();
        responseLog.put("requestId", requestId);
        responseLog.put("body", parseJsonBody(body));

        logAsJson(responseLog, RESPONSE_LOG_PREFIX, ERROR_SERIALIZING_RESPONSE);
    }

    private Object parseJsonBody(String body) {
        try {
            return objectMapper.readValue(body, Map.class);
        } catch (Exception e) {
            return body;
        }
    }

    private void logAsJson(Map<String, Object> logData, String logPrefix, String errorMessage) {
        try {
            log.info(logPrefix, objectMapper.writeValueAsString(logData));
        } catch (Exception e) {
            log.error(errorMessage, e);
        }
    }
}