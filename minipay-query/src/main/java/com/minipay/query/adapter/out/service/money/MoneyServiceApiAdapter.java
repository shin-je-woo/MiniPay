package com.minipay.query.adapter.out.service.money;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.query.application.port.out.MoneyInfo;
import com.minipay.query.application.port.out.MoneyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.*;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MoneyServiceApiAdapter implements MoneyServicePort {

    private final MoneyFeignClient moneyFeignClient;

    @Override
    public List<MoneyInfo> getMoneyInfosByMembershipIds(List<UUID> membershipIds) {
        return Optional.ofNullable(moneyFeignClient.getMemberMoneyListByMembershipIds(membershipIds))
                .map(ResponseEntity::getBody)
                .map(MemberMoneyListResponse::memberMoneyList)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(memberMoneyResponse -> new MoneyInfo(
                        memberMoneyResponse.memberMoneyId(),
                        memberMoneyResponse.membershipId(),
                        memberMoneyResponse.balance())
                )
                .toList();
    }
}