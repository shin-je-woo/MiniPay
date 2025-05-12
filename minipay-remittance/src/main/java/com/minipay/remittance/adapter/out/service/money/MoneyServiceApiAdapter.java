package com.minipay.remittance.adapter.out.service.money;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.common.exception.DataNotFoundException;
import com.minipay.remittance.application.port.out.MoneyInfo;
import com.minipay.remittance.application.port.out.MoneyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MoneyServiceApiAdapter implements MoneyServicePort {

    private final MoneyFeignClient moneyFeignClient;

    @Override
    public MoneyInfo getMoneyInfo(UUID membershipId) {
        return Optional.ofNullable(moneyFeignClient.getMemberMoneyByMembershipId(membershipId))
                .map(ResponseEntity::getBody)
                .map(memberMoneyResponse -> new MoneyInfo(
                        memberMoneyResponse.memberMoneyId(),
                        memberMoneyResponse.bankAccountId(),
                        memberMoneyResponse.balance()
                ))
                .orElseThrow(() -> new DataNotFoundException("Member money not found for membership id: " + membershipId));
    }

    @Override
    public boolean rechargeMoney(UUID memberMoneyId, BigDecimal amount) {
        ResponseEntity<MemberMoneyResponse> response = moneyFeignClient.requestMemberMoneyRecharge(
                memberMoneyId, RechargeMoneyRequest.from(amount)
        );
        return response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public boolean decreaseMoney(UUID memberMoneyId, BigDecimal amount) {
        ResponseEntity<MemberMoneyResponse> response = moneyFeignClient.decreaseMemberMoney(
                memberMoneyId, DecreaseMoneyRequest.from(amount)
        );
        return response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public boolean increaseMoney(UUID memberMoneyId, BigDecimal amount) {
        ResponseEntity<MemberMoneyResponse> response = moneyFeignClient.increaseMemberMoney(
                memberMoneyId, IncreaseMoneyRequest.from(amount)
        );
        return response.getStatusCode().is2xxSuccessful();
    }
}
