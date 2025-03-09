package com.minipay.remittance.adapter.out.service.banking;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.remittance.application.port.out.BankingServicePort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class BankingServiceApiAdapter implements BankingServicePort {

    // TODO IPC 구현
    @Override
    public boolean withdrawalMinipayFund(String bankName, String bankAccountNumber, BigDecimal amount) {
        return true;
    }
}
