package com.minipay.membership.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.membership.adapter.in.web.request.ModifyMembershipRequest;
import com.minipay.membership.adapter.in.web.request.RegisterMembershipRequest;
import com.minipay.membership.adapter.in.web.response.MembershipByAddressResponse;
import com.minipay.membership.adapter.in.web.response.MembershipResponse;
import com.minipay.membership.application.port.in.*;
import com.minipay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class MembershipController {

    private final RegisterMembershipUseCase registerMembershipUseCase;
    private final GetMembershipUseCase getMembershipUseCase;
    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @PostMapping("/membership")
    public ResponseEntity<MembershipResponse> registerMembership(@RequestBody RegisterMembershipRequest request) {
        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.name())
                .email(request.email())
                .address(request.address())
                .isValid(true)
                .isCorp(request.isCorp())
                .build();
        Membership membership = registerMembershipUseCase.registerMembership(command);

        return ResponseEntity.ok(MembershipResponse.from(membership));
    }

    @GetMapping("/membership/{membershipId}")
    public ResponseEntity<MembershipResponse> getMembership(@PathVariable UUID membershipId) {
        Membership membership = getMembershipUseCase.getMembership(membershipId);

        return ResponseEntity.ok(MembershipResponse.from(membership));
    }

    @PutMapping("/membership/{membershipId}")
    public ResponseEntity<MembershipResponse> modifyMembership(
            @PathVariable UUID membershipId,
            @RequestBody ModifyMembershipRequest request
    ) {
        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .membershipId(membershipId)
                .name(request.name())
                .email(request.email())
                .address(request.address())
                .build();
        Membership membership = modifyMembershipUseCase.modifyMembership(command);

        return ResponseEntity.ok(MembershipResponse.from(membership));
    }

    @GetMapping("/membership")
    public ResponseEntity<MembershipByAddressResponse> getMembershipByAddress(@RequestParam String address) {
        List<Membership> memberships = getMembershipUseCase.getMembershipByAddress(address);
        return ResponseEntity.ok(MembershipByAddressResponse.from(memberships));
    }
}
