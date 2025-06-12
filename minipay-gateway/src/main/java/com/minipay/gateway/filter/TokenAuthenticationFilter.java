package com.minipay.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minipay.gateway.component.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class TokenAuthenticationFilter extends AbstractGatewayFilterFactory<TokenAuthenticationFilter.Config> {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    public TokenAuthenticationFilter(TokenProvider tokenProvider, ObjectMapper objectMapper) {
        super(Config.class);
        this.tokenProvider = tokenProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            try {
                ServerHttpRequest request = exchange.getRequest();
                String token = tokenProvider.getToken(request);
                tokenProvider.resolveToken(token);
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    ServerHttpResponse response = exchange.getResponse();
                    log.info(response.toString());
                }));
            } catch (ExpiredJwtException e) {
                return unauthorizedResponse(exchange, "Expired token");
            } catch (MalformedJwtException e) {
                return unauthorizedResponse(exchange, "Malformed token");
            } catch (SignatureException e) {
                return unauthorizedResponse(exchange, "Invalid signature");
            } catch (Exception e) {
                return unauthorizedResponse(exchange, "Invalid or missing token");
            }
        };
    }

    @SneakyThrows(JsonProcessingException.class)
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String errorMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponse("Unauthorized", errorMessage);
        DataBuffer buffer = response.bufferFactory().wrap(
                objectMapper.writeValueAsString(errorResponse).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }


    private record ErrorResponse(String message, String reason) {
    }

    public static class Config {

    }
}
