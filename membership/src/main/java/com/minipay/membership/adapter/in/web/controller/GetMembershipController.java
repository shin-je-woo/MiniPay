package com.minipay.membership.adapter.in.web.controller;

import com.minipay.common.WebAdapter;
import com.minipay.membership.application.port.in.GetMembershipQuery;
import com.minipay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetMembershipController {

    private final GetMembershipQuery getMembershipQuery;

    @GetMapping("/membership/{membershipId}")
    public ResponseEntity<Membership> getMembership(@PathVariable Long membershipId) {
        Membership membership = getMembershipQuery.getMembership(membershipId);
        return ResponseEntity.ok(membership);
    }
}
