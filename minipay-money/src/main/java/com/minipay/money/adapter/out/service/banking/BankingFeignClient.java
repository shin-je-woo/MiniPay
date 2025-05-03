package com.minipay.money.adapter.out.service.banking;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "banking-service", url = "${minipay.banking.url}")
public interface BankingFeignClient {

    @GetMapping("/bank-accounts")
    ResponseEntity<BankAccountListResponse> getBankAccountsByBankName(@RequestParam String bankName);
}
