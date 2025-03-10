package com.minipay.remittance.adapter.out.service.money;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.remittance.application.port.out.MoneyInfo;
import com.minipay.remittance.application.port.out.MoneyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MoneyServiceApiAdapter implements MoneyServicePort {

    private final MoneyFeignClient moneyFeignClient;

    @Override
    public MoneyInfo getMoneyInfo(UUID membershipId) {
        MemberMoneyResponse memberMoneyResponse = moneyFeignClient.getMemberMoney(membershipId).getBody();
        return MoneyInfo.builder()
                .memberMoneyId(memberMoneyResponse.memberMoneyId())
                .balance(memberMoneyResponse.balance())
                .build();
    }

    @Override
    public boolean decreaseMoney(UUID memberMoneyId, BigDecimal amount) {
        return true;
    }

    @Override
    public boolean increaseMoney(UUID memberMoneyId, BigDecimal amount) {
        ResponseEntity<MemberMoneyResponse> response = moneyFeignClient.increaseMemberMoneyRequest(
                memberMoneyId, IncreaseMoneyRequest.from(amount)
        );
        return response.getStatusCode().is2xxSuccessful();
    }
}
