package com.minipay.query.adapter.out.service.money;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.query.application.port.out.MoneyInfo;
import com.minipay.query.application.port.out.MoneyServicePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MoneyServiceApiAdapter implements MoneyServicePort {

    private final MoneyFeignClient moneyFeignClient;

    @Override
    public List<MoneyInfo> getMoneyInfosByMembershipIds(List<UUID> membershipIds) {
        MemberMoneyListResponse memberMoneyListResponse = moneyFeignClient.getMemberMoneyListByMembershipIds(membershipIds).getBody();
        List<MemberMoneyResponse> memberMoneyResponses = memberMoneyListResponse.memberMoneyList();
        return memberMoneyResponses.stream()
                .map(memberMoneyResponse -> new MoneyInfo(
                        memberMoneyResponse.memberMoneyId(),
                        memberMoneyResponse.membershipId(),
                        memberMoneyResponse.balance()
                ))
                .toList();
    }
}