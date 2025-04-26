package com.minipay.money.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.money.adapter.in.web.request.DecreaseMoneyRequest;
import com.minipay.money.adapter.in.web.response.MemberMoneyResponse;
import com.minipay.money.application.port.in.DecreaseMoneyCommand;
import com.minipay.money.application.port.in.DecreaseMoneyUseCase;
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
public class DecreaseMoneyController {

    private final DecreaseMoneyUseCase decreaseMoneyUseCase;

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

    @PostMapping("/member-money/{memberMoneyId}/decrease-axon")
    ResponseEntity<Void> decreaseMemberMoneyAxon(
            @PathVariable UUID memberMoneyId, @RequestBody DecreaseMoneyRequest request
    ) {
        DecreaseMoneyCommand command = DecreaseMoneyCommand.builder()
                .memberMoneyId(memberMoneyId)
                .amount(request.amount())
                .build();
        decreaseMoneyUseCase.decreaseMoneyByAxon(command);
        return ResponseEntity.ok().build();
    }
}
