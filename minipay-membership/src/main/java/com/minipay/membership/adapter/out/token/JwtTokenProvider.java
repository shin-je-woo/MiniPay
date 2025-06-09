package com.minipay.membership.adapter.out.token;

import com.minipay.membership.application.config.TokenProperties;
import com.minipay.membership.application.port.out.TokenProvider;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    private final TokenProperties tokenProperties;

    @Override
    public String createAccessToken(UUID membershipId) {
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(membershipId.toString())
                .issuer("Minipay")
                .expiration(new Date(System.currentTimeMillis() + tokenProperties.getAccessExpirationTime()))
                .signWith(tokenProperties.getSecretKey())
                .compact();
    }
}
