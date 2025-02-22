package com.minipay.money.adapter.out.service;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.money.application.port.out.GetMembershipPort;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MembershipServiceAdapter implements GetMembershipPort {

    private final MembershipFeignClient membershipFeignClient;

    @Override
    public boolean isValidMembership(UUID membershipId) {
        MembershipResponse membershipResponse = Objects.requireNonNull(membershipFeignClient.getMembership(membershipId).getBody());
        return membershipResponse.isValid();
    }
}
