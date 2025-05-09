package com.minipay.remittance.adapter.out.service.banking;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.remittance.application.port.out.BankingServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class BankingServiceApiAdapter implements BankingServicePort {

    private final BankingFeignClient bankingFeignClient;

    @Override
    public boolean withdrawalFund(UUID senderBankAccountId, String bankName, String bankAccountNumber, BigDecimal amount) {
        WithdrawalFundRequest request = WithdrawalFundRequest.builder()
                .bankAccountId(senderBankAccountId)
                .bankName(bankName)
                .bankAccountNumber(bankAccountNumber)
                .amount(amount)
                .build();
        ResponseEntity<Void> response = bankingFeignClient.withdrawalFund(request);
        return response.getStatusCode().is2xxSuccessful();
    }
}
