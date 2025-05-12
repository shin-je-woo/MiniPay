package com.minipay.query.adapter.out.service.membership;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.common.exception.DataNotFoundException;
import com.minipay.query.application.port.out.MembershipInfo;
import com.minipay.query.application.port.out.MembershipServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.*;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MembershipServiceApiAdapter implements MembershipServicePort {

    private final MembershipFeignClient membershipFeignClient;

    @Override
    public MembershipInfo getMembershipInfo(UUID membershipId) {
        return Optional.ofNullable(membershipFeignClient.getMembership(membershipId))
                .map(ResponseEntity::getBody)
                .map(membershipResponse -> new MembershipInfo(
                        membershipResponse.membershipId(),
                        membershipResponse.address()
                ))
                .orElseThrow(() -> new DataNotFoundException("Membership not found for membershipId: " + membershipId));
    }

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
