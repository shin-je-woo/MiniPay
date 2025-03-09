package com.minipay.remittance.adapter.out.service.money;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.remittance.application.port.out.MoneyInfo;
import com.minipay.remittance.application.port.out.MoneyServicePort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class MoneyServiceApiAdapter implements MoneyServicePort {

    // TODO IPC 구현
    @Override
    public MoneyInfo getMoneyInfo(UUID membershipId) {
        return MoneyInfo.builder()
                .memberMoneyId(UUID.randomUUID())
                .balance(BigDecimal.valueOf(100_000_000))
                .build();
    }

    @Override
    public boolean decreaseMoney(UUID memberMoneyId, BigDecimal amount) {
        return true;
    }

    @Override
    public boolean increaseMoney(UUID memberMoneyId, BigDecimal amount) {
        return true;
    }
}
