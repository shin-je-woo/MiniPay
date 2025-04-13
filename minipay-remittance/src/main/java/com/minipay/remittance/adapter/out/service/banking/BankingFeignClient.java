package com.minipay.remittance.adapter.out.service.banking;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "banking-service", url = "${minipay.banking.url}")
public interface BankingFeignClient {

    @GetMapping("/fund-transactions/withdrawal")
    ResponseEntity<Void> withdrawalFund(@RequestBody WithdrawalFundRequest request);
}
