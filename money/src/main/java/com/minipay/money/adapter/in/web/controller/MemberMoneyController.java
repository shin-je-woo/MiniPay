package com.minipay.money.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.money.adapter.in.web.request.CreateMoneyRequest;
import com.minipay.money.adapter.in.web.request.DecreaseMoneyRequest;
import com.minipay.money.adapter.in.web.request.IncreaseMoneyRequest;
import com.minipay.money.adapter.in.web.request.RechargeMoneyRequest;
import com.minipay.money.adapter.in.web.response.MemberMoneyResponse;
import com.minipay.money.application.port.in.*;
import com.minipay.money.domain.model.MemberMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@WebAdapter
@RestController("/internal")
@RequiredArgsConstructor
public class MemberMoneyController {

    private final RegisterMemberMoneyUseCase registerMemberMoneyUseCase;
    private final RechargeMoneyUseCase rechargeMoneyUseCase;
    private final IncreaseMoneyUseCase increaseMoneyUseCase;
    private final DecreaseMoneyUseCase decreaseMoneyUseCase;
    private final MemberMoneyQuery memberMoneyQuery;

    @PostMapping("/member-money/{memberMoneyId}/recharge")
    ResponseEntity<MemberMoneyResponse> requestMemberMoneyRecharge(
            @PathVariable UUID memberMoneyId, @RequestBody RechargeMoneyRequest request
    ) {
        RequestMoneyRechargeCommand command = RequestMoneyRechargeCommand.builder()
                .memberMoneyId(memberMoneyId)
                .amount(request.amount())
                .build();
        MemberMoney memberMoney = rechargeMoneyUseCase.requestMoneyRecharge(command);
        return ResponseEntity.ok(MemberMoneyResponse.from(memberMoney));
    }

    @PostMapping("/member-money/{memberMoneyId}/increase")
    ResponseEntity<MemberMoneyResponse> increaseMemberMoney(
            @PathVariable UUID memberMoneyId, @RequestBody IncreaseMoneyRequest request
    ) {
        IncreaseMoneyCommand command = IncreaseMoneyCommand.builder()
                .memberMoneyId(memberMoneyId)
                .amount(request.amount())
                .build();
        MemberMoney memberMoney = increaseMoneyUseCase.increaseMoney(command);
        return ResponseEntity.ok(MemberMoneyResponse.from(memberMoney));
    }

    @PostMapping("/member-money/{memberMoneyId}/decrease")
    ResponseEntity<MemberMoneyResponse> decreaseMemberMoney(
            @PathVariable UUID memberMoneyId, @RequestBody DecreaseMoneyRequest request
    ) {
        DecreaseMoneyCommand command = DecreaseMoneyCommand.builder()
                .memberMoneyId(memberMoneyId)
                .amount(request.amount())
                .build();
        MemberMoney memberMoney = decreaseMoneyUseCase.decreaseMoney(command);
        return ResponseEntity.ok(MemberMoneyResponse.from(memberMoney));
    }

    @PostMapping("/member-money")
    ResponseEntity<Void> createMemberMoney(@RequestBody CreateMoneyRequest request) {
        RegisterMemberMoneyCommand command = RegisterMemberMoneyCommand.builder()
                .membershipId(request.membershipId())
                .bankAccountId(request.bankAccountId())
                .build();
        registerMemberMoneyUseCase.registerMemberMoneyAxon(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/member-money")
    ResponseEntity<MemberMoneyResponse> getMemberMoney(@RequestParam UUID membershipId) {
        MemberMoney memberMoney = memberMoneyQuery.getMemberMoney(membershipId);
        return ResponseEntity.ok(MemberMoneyResponse.from(memberMoney));
    }
}
