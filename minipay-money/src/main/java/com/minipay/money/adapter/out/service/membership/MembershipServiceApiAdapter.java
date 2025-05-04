package com.minipay.money.adapter.out.service.membership;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.money.application.port.out.MembershipServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MembershipServiceApiAdapter implements MembershipServicePort {

    private final MembershipFeignClient membershipFeignClient;

    @Override
    public boolean isValidMembership(UUID membershipId) {
        return Optional.ofNullable(membershipFeignClient.getMembership(membershipId))
                .map(ResponseEntity::getBody)
                .map(MembershipResponse::isValid)
                .orElse(false);
    }
}
