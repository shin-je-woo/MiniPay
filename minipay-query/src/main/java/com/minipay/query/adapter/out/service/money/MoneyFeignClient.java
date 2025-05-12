package com.minipay.query.adapter.out.service.money;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "money-service", url = "${minipay.money.url}")
public interface MoneyFeignClient {

    @GetMapping("/member-money")
    ResponseEntity<MemberMoneyResponse> getMemberMoneyByMembershipId(@RequestParam UUID membershipId);

    @GetMapping("/member-money/{memberMoneyId}")
    ResponseEntity<MemberMoneyResponse> getMemberMoney(@PathVariable UUID memberMoneyId);

    @GetMapping("/member-money/list")
    ResponseEntity<MemberMoneyListResponse> getMemberMoneyListByMembershipIds(@RequestParam List<UUID> membershipIds);
}