package com.minipay.banking.adapter.out.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "membership-service", url = "${minipay.membership.url}")
public interface MembershipFeignClient {

    @GetMapping("/membership/{membershipId}")
    ResponseEntity<MembershipResponse> getMembership(@PathVariable UUID membershipId);

    @GetMapping("/membership")
    ResponseEntity<MembershipByAddressResponse> getMembershipByAddress(@RequestParam String address);
}
