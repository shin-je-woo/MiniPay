package com.minipay.money.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.money.adapter.in.web.request.DecreaseMoneyRequest;
import com.minipay.money.adapter.in.web.request.IncreaseMoneyRequest;
import com.minipay.money.adapter.in.web.response.MemberMoneyResponse;
import com.minipay.money.application.port.in.*;
import com.minipay.money.domain.MemberMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class MemberMoneyController {

    private final IncreaseMoneyUseCase increaseMoneyUseCase;
    private final DecreaseMoneyUseCase decreaseMoneyUseCase;
    private final MemberMoneyQuery memberMoneyQuery;

    /**
     * TODO 권한 검사 필요할 듯
     */
    @PostMapping("/member-money/{memberMoneyId}/increase")
    ResponseEntity<MemberMoneyResponse> increaseMemberMoneyRequest(
            @PathVariable UUID memberMoneyId, @RequestBody IncreaseMoneyRequest request
    ) {
        RequestMoneyIncreaseCommand command = RequestMoneyIncreaseCommand.builder()
                .memberMoneyId(memberMoneyId)
                .amount(request.amount())
                .build();
        MemberMoney memberMoney = increaseMoneyUseCase.requestMoneyIncrease(command);
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

    @GetMapping("/member-money")
    ResponseEntity<MemberMoneyResponse> getMemberMoney(@RequestParam UUID membershipId) {
        MemberMoney memberMoney = memberMoneyQuery.getMemberMoney(membershipId);
        return ResponseEntity.ok(MemberMoneyResponse.from(memberMoney));
    }
}
