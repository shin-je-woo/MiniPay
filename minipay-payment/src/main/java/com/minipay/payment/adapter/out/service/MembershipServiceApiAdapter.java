package com.minipay.payment.adapter.out.service;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.payment.application.port.out.MembershipServicePort;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MembershipServiceApiAdapter implements MembershipServicePort {

    private final MembershipFeignClient membershipFeignClient;

    @Override
    public boolean isValidMembership(UUID membershipId) {
        MembershipResponse membershipResponse = Objects.requireNonNull(membershipFeignClient.getMembership(membershipId).getBody());
        return membershipResponse.isValid();
    }
}
