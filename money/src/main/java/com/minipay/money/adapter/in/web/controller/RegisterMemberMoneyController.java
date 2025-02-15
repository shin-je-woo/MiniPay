package com.minipay.money.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.money.adapter.in.web.request.RegisterMemberMoneyRequest;
import com.minipay.money.application.port.in.RegisterMemberMoneyCommand;
import com.minipay.money.application.port.in.RegisterMemberMoneyUseCase;
import com.minipay.money.domain.MemberMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMemberMoneyController {

    private final RegisterMemberMoneyUseCase registerMemberMoneyUseCase;

    /**
     * TODO Money 등록은 Eventual Consistency 보장을 위해 이벤트 기반으로 변경해야 함
     */
    @PostMapping("/member-money")
    ResponseEntity<MemberMoney> registerMemberMoney(@RequestBody RegisterMemberMoneyRequest request) {
        RegisterMemberMoneyCommand command = RegisterMemberMoneyCommand.builder()
                .membershipId(request.membershipId())
                .bankAccountId(request.bankAccountId())
                .build();

        return ResponseEntity.ok(registerMemberMoneyUseCase.registerMemberMoney(command));
    }
}
