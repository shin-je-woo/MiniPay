package com.minipay.remittance.adapter.out.service.money;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "money-service", url = "${minipay.money.url}")
public interface MoneyFeignClient {

    @GetMapping("/member-money")
    ResponseEntity<MemberMoneyResponse> getMemberMoney(@RequestParam UUID membershipId);

    @PostMapping("/member-money/{memberMoneyId}/recharge")
    ResponseEntity<MemberMoneyResponse> requestMemberMoneyRecharge(
            @PathVariable UUID memberMoneyId, @RequestBody RechargeMoneyRequest request
    );

    @PostMapping("/member-money/{memberMoneyId}/increase")
    ResponseEntity<MemberMoneyResponse> increaseMemberMoney(
            @PathVariable UUID memberMoneyId, @RequestBody IncreaseMoneyRequest request
    );

    @PostMapping("/member-money/{memberMoneyId}/decrease")
    ResponseEntity<MemberMoneyResponse> decreaseMemberMoney(
            @PathVariable UUID memberMoneyId, @RequestBody DecreaseMoneyRequest request
    );
}
