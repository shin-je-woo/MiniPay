package com.minipay.query.adapter.out.service.membership;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.query.application.port.out.MembershipServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.*;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MembershipServiceApiAdapter implements MembershipServicePort {

    private final MembershipFeignClient membershipFeignClient;

    @Override
    public List<UUID> getMembershipIdsByAddress(String address) {
        return Optional.ofNullable(membershipFeignClient.getMembershipByAddress(address))
                .map(ResponseEntity::getBody)
                .map(MembershipByAddressResponse::memberships)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(MembershipResponse::membershipId)
                .toList();
    }
}
