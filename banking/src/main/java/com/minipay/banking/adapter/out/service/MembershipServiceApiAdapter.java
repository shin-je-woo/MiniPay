package com.minipay.banking.adapter.out.service;

import com.minipay.banking.application.port.out.MembershipServicePort;
import com.minipay.common.annotation.MiniPayServiceAdapter;
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
