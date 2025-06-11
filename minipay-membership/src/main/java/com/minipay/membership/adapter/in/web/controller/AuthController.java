package com.minipay.membership.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.membership.adapter.in.web.request.LoginMembershipRequest;
import com.minipay.membership.adapter.in.web.request.ReIssueTokenRequest;
import com.minipay.membership.application.port.in.LoginMembershipCommand;
import com.minipay.membership.application.port.in.LoginMembershipResult;
import com.minipay.membership.application.port.in.LoginMembershipUseCase;
import com.minipay.membership.application.port.in.ReIssueTokenCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final LoginMembershipUseCase loginMembershipUseCase;

    @PostMapping("/auth/tokens")
    public ResponseEntity<LoginMembershipResult> loginMembership(@RequestBody LoginMembershipRequest request) {
        LoginMembershipCommand command = LoginMembershipCommand.builder()
                .email(request.email())
                .password(request.password())
                .build();
        LoginMembershipResult loginMembershipResult = loginMembershipUseCase.login(command);

        return ResponseEntity.ok(loginMembershipResult);
    }

    @PostMapping("/auth/tokens/re-issue")
    public ResponseEntity<LoginMembershipResult> reIssueToken(@RequestBody ReIssueTokenRequest request) {
        ReIssueTokenCommand command = ReIssueTokenCommand.builder()
                .refreshToken(request.refreshToken())
                .build();
        LoginMembershipResult loginMembershipResult = loginMembershipUseCase.reIssueToken(command);

        return ResponseEntity.ok(loginMembershipResult);
    }
}
