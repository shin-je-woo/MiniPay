package com.minipay.banking.adapter.out.service;

import com.minipay.banking.application.port.out.MembershipServicePort;
import com.minipay.common.annotation.MiniPayServiceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
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

    @Override
    public List<UUID> getMembershipIdsByAddress(String address) {
        return Optional.ofNullable(membershipFeignClient.getMembershipByAddress(address))
                .map(ResponseEntity::getBody)
                .map(MembershipByAddressResponse::memberships)
                .orElse(Collections.emptyList())
                .stream()
                .map(MembershipResponse::membershipId)
                .toList();
    }
}
