package com.minipay.membership.adapter.out.token;

import com.minipay.membership.adapter.out.redis.RedisOperator;
import com.minipay.membership.application.config.TokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisOperator redisOperator;
    private final TokenProperties tokenProperties;
    private static final String PREFIX_REFRESH_TOKEN = "refreshToken:";

    public void save(String refreshToken, UUID membershipId) {
        redisOperator.put(PREFIX_REFRESH_TOKEN + refreshToken, membershipId, tokenProperties.getRefreshExpirationTime());
    }
}
