package com.minipay.membership.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.membership.adapter.in.web.request.ModifyMembershipRequest;
import com.minipay.membership.adapter.in.web.response.MembershipResponse;
import com.minipay.membership.application.port.in.ModifyMembershipCommand;
import com.minipay.membership.application.port.in.ModifyMembershipUseCase;
import com.minipay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {

    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @PutMapping("/membership/{membershipId}")
    public ResponseEntity<MembershipResponse> modifyMembership(
            @PathVariable Long membershipId,
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
}
