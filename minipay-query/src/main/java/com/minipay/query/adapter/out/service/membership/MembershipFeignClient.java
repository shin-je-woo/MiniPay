package com.minipay.query.adapter.out.service.membership;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "membership-service", url = "${minipay.membership.url}")
public interface MembershipFeignClient {

    @GetMapping("/membership")
    ResponseEntity<MembershipByAddressResponse> getMembershipByAddress(@RequestParam String address);
}
