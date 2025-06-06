package com.minipay.money.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.money.adapter.in.web.request.IncreaseMoneyRequest;
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
public class IncreaseMoneyController {

    private final IncreaseMoneyUseCase increaseMoneyUseCase;

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

    @PostMapping("/member-money/{memberMoneyId}/increase-axon")
    ResponseEntity<MemberMoneyResponse> increaseMemberMoneyByAxon(
            @PathVariable UUID memberMoneyId, @RequestBody IncreaseMoneyRequest request
    ) {
        IncreaseMoneyCommand command = IncreaseMoneyCommand.builder()
                .memberMoneyId(memberMoneyId)
                .amount(request.amount())
                .build();
        increaseMoneyUseCase.increaseMoneyByAxon(command);
        return ResponseEntity.ok().build();
    }
}
