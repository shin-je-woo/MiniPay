package com.minipay.membership.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.membership.adapter.in.web.request.RegisterMembershipRequest;
import com.minipay.membership.adapter.in.web.response.MembershipResponse;
import com.minipay.membership.application.port.in.RegisterMembershipCommand;
import com.minipay.membership.application.port.in.RegisterMembershipUseCase;
import com.minipay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {

    private final RegisterMembershipUseCase registerMembershipUseCase;

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
}
