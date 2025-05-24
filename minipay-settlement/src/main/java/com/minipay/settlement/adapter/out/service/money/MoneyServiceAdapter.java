package com.minipay.settlement.adapter.out.service.money;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.common.exception.DataNotFoundException;
import com.minipay.settlement.port.out.MoneyInfo;
import com.minipay.settlement.port.out.MoneyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements MoneyServicePort {

    private final MoneyFeignClient moneyFeignClient;

    @Override
    public MoneyInfo getMoneyInfoBySellerId(UUID sellerId) {
        return Optional.ofNullable(moneyFeignClient.getMemberMoneyByMembershipId(sellerId))
                .map(ResponseEntity::getBody)
                .map(memberMoneyResponse -> new MoneyInfo(
                        memberMoneyResponse.memberMoneyId(),
                        memberMoneyResponse.bankAccountId()
                ))
                .orElseThrow(() -> new DataNotFoundException("Member money not found for sellerId: " + sellerId));
    }
}
