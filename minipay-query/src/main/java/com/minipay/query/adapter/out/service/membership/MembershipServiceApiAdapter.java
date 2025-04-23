package com.minipay.query.adapter.out.service.membership;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.query.application.port.out.MembershipServicePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MembershipServiceApiAdapter implements MembershipServicePort {

    private final MembershipFeignClient membershipFeignClient;

    @Override
    public List<UUID> getMembershipIdsByAddress(String address) {
        MembershipByAddressResponse membershipByAddressResponse = membershipFeignClient.getMembershipByAddress(address).getBody();
        return membershipByAddressResponse.memberships().stream()
                .map(MembershipResponse::membershipId)
                .toList();
    }
}
