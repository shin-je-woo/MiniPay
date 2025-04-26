package com.minipay.money.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.money.adapter.in.web.request.RechargeMoneyRequest;
import com.minipay.money.adapter.in.web.response.MemberMoneyResponse;
import com.minipay.money.application.port.in.*;
import com.minipay.money.domain.model.MemberMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RechargeMoneyController {

    private final RechargeMoneyUseCase rechargeMoneyUseCase;

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

    @PostMapping("/member-money/{memberMoneyId}/recharge-axon")
    ResponseEntity<Void> requestMemberMoneyRechargeByAxon(
            @PathVariable UUID memberMoneyId, @RequestBody RechargeMoneyRequest request
    ) {
        RequestMoneyRechargeCommand command = RequestMoneyRechargeCommand.builder()
                .memberMoneyId(memberMoneyId)
                .amount(request.amount())
                .build();
        rechargeMoneyUseCase.requestMoneyRechargeByAxon(command);
        return ResponseEntity.ok().build();
    }
}
