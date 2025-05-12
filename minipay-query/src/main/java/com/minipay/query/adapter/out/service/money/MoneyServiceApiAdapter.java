package com.minipay.query.adapter.out.service.money;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.common.exception.DataNotFoundException;
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
    public MoneyInfo getMoneyInfo(UUID memberMoneyId) {
        return Optional.ofNullable(moneyFeignClient.getMemberMoney(memberMoneyId))
                .map(ResponseEntity::getBody)
                .map(memberMoneyResponse -> new MoneyInfo(
                        memberMoneyResponse.memberMoneyId(),
                        memberMoneyResponse.membershipId(),
                        memberMoneyResponse.balance()
                ))
                .orElseThrow(() -> new DataNotFoundException("Member money not found for memberMoneyId: " + memberMoneyId));
    }

    @Override
    public MoneyInfo getMoneyInfoByMembershipId(UUID membershipId) {
        return Optional.ofNullable(moneyFeignClient.getMemberMoneyByMembershipId(membershipId))
                .map(ResponseEntity::getBody)
                .map(memberMoneyResponse -> new MoneyInfo(
                        memberMoneyResponse.memberMoneyId(),
                        memberMoneyResponse.bankAccountId(),
                        memberMoneyResponse.balance()
                ))
                .orElseThrow(() -> new DataNotFoundException("Member money not found for membershipId: " + membershipId));
    }

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