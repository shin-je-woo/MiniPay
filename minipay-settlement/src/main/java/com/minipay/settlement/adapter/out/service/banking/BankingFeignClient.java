package com.minipay.settlement.adapter.out.service.banking;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "banking-service", url = "${minipay.banking.url}")
public interface BankingFeignClient {

    @GetMapping("/bank-accounts/{bankAccountId}")
    ResponseEntity<BankAccountResponse> getBankAccount(@PathVariable UUID bankAccountId);

    @GetMapping("/fund-transactions/withdrawal")
    ResponseEntity<Void> withdrawalFund(@RequestBody WithdrawalFundRequest request);
}
