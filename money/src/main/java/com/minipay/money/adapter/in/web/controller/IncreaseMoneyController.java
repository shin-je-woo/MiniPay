package com.minipay.money.adapter.in.web.controller;

import com.minipay.common.WebAdapter;
import com.minipay.money.adapter.in.web.request.IncreaseMoneyRequest;
import com.minipay.money.application.port.in.IncreaseMoneyCommand;
import com.minipay.money.application.port.in.IncreaseMoneyUseCase;
import com.minipay.money.domain.MemberMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class IncreaseMoneyController {

    private final IncreaseMoneyUseCase increaseMoneyUseCase;

    /**
     * TODO 권한 검사 필요할 듯
     */
    @PostMapping("member-money/{memberMoneyId}/increase")
    ResponseEntity<MemberMoney> increaseMemberMoney(
            @PathVariable Long memberMoneyId,
            @RequestBody IncreaseMoneyRequest request
    ) {
        IncreaseMoneyCommand command = IncreaseMoneyCommand.builder()
                .memberMoneyId(memberMoneyId)
                .amount(request.amount())
                .build();

        return ResponseEntity.ok(increaseMoneyUseCase.increaseMoney(command));
    }
}
