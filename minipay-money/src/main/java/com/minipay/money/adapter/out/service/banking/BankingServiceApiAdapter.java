package com.minipay.money.adapter.out.service.banking;

import com.minipay.common.annotation.MiniPayServiceAdapter;
import com.minipay.money.application.port.out.BankAccountInfo;
import com.minipay.money.application.port.out.BankingServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@MiniPayServiceAdapter
@RequiredArgsConstructor
public class BankingServiceApiAdapter implements BankingServicePort {

    private final BankingFeignClient bankingFeignClient;

    @Override
    public List<BankAccountInfo> getBankAccountsByBankName(String bankName) {
        return Optional.ofNullable(bankingFeignClient.getBankAccountsByBankName(bankName))
                .map(ResponseEntity::getBody)
                .map(BankAccountListResponse::bankAccounts)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(bankAccountResponse -> new BankAccountInfo(
                        bankAccountResponse.bankAccountId(),
                        bankAccountResponse.membershipId())
                )
                .toList();
    }
}