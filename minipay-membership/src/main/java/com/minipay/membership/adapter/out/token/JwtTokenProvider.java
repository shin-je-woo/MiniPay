package com.minipay.membership.adapter.out.token;

import com.minipay.membership.adapter.out.redis.RedisOperator;
import com.minipay.membership.application.config.TokenProperties;
import com.minipay.membership.application.port.out.TokenProvider;
import com.minipay.membership.domain.Membership;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    private final TokenProperties tokenProperties;
    private final RedisOperator redisOperator;
    private static final String PREFIX_REFRESH_TOKEN = "refreshToken:";

    @Override
    public String createAccessToken(Membership.MembershipId membershipId) {
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(membershipId.value().toString())
                .issuer("Minipay")
                .expiration(new Date(System.currentTimeMillis() + tokenProperties.getAccessExpirationTime()))
                .signWith(tokenProperties.getSecretKey())
                .compact();
    }

    @Override
    public String createRefreshToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void saveRefreshToken(String refreshToken, Membership.MembershipId membershipId) {
        redisOperator.put(PREFIX_REFRESH_TOKEN + refreshToken, membershipId.value(), tokenProperties.getRefreshExpirationTime());
    }

    @Override
    public Optional<Membership.MembershipId> getMembershipIdByRefreshToken(String refreshToken) {
        return redisOperator.get(PREFIX_REFRESH_TOKEN + refreshToken)
                .map(value -> new Membership.MembershipId(UUID.fromString(value.toString())));
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        redisOperator.delete(PREFIX_REFRESH_TOKEN + refreshToken);
    }
}
