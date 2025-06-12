package com.minipay.gateway.component;

import com.minipay.gateway.config.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final TokenProperties tokenProperties;

    public String getToken(ServerHttpRequest request) {
        String tokenValue = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(tokenValue) || !tokenValue.startsWith(tokenProperties.getTokenType() + " ")) {
            log.error("토큰이 비어있거나 잘못된 형식입니다.");
            throw new JwtException("Invalid token format");
        }
        return tokenValue.substring(tokenProperties.getTokenType().length() + 1);
    }

    public String resolveToken(String token) {
        Jws<Claims> claims = parseToken(token);
        return claims.getPayload().getSubject();
    }

    private Jws<Claims> parseToken(String token) {
        Jws<Claims> claims;
        try {
            claims = Jwts.parser().verifyWith(tokenProperties.getSecretKey()).build().parseSignedClaims(token);
        } catch (JwtException e) {
            log.error("token 파싱 중 에러발생! {} {}", e.getClass(), e.getMessage());
            throw e;
        }
        return claims;
    }
}
