package com.minipay.settlement.adapter.out.service.banking;


import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.settlement.port.out.BankAccountInfo;
import com.minipay.settlement.port.out.BankingServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class BankingServiceApiAdapter implements BankingServicePort {

    private final BankingFeignClient bankingFeignClient;

    @Override
    public BankAccountInfo getBankAccountInfo(UUID bankAccountId) {
        return Optional.ofNullable(bankingFeignClient.getBankAccount(bankAccountId))
                .map(ResponseEntity::getBody)
                .map(bankAccountResponse -> new BankAccountInfo(
                        bankAccountResponse.bankAccountId(),
                        bankAccountResponse.linkedBankName(),
                        bankAccountResponse.linkedAccountNumber()
                ))
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found for ID: " + bankAccountId));
    }

    @Override
    public boolean transferSettlementAmount(UUID bankAccountId, String bankName, String bankAccountNumber, BigDecimal amount) {
        WithdrawalFundRequest request = WithdrawalFundRequest.builder()
                .bankAccountId(bankAccountId)
                .bankName(bankName)
                .bankAccountNumber(bankAccountNumber)
                .amount(amount)
                .build();
        ResponseEntity<Void> response = bankingFeignClient.withdrawalFund(request);
        return response.getStatusCode().is2xxSuccessful();
    }
}
