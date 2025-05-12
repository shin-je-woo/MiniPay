package com.minipay.money.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.money.adapter.in.web.request.*;
import com.minipay.money.adapter.in.web.response.MemberMoneyListResponse;
import com.minipay.money.adapter.in.web.response.MemberMoneyResponse;
import com.minipay.money.application.port.in.*;
import com.minipay.money.domain.model.MemberMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class MemberMoneyController {

    private final RegisterMemberMoneyUseCase registerMemberMoneyUseCase;
    private final GetMemberMoneyUseCase getMemberMoneyUseCase;

    @PostMapping("/member-money/axon")
    ResponseEntity<Void> createMemberMoney(@RequestBody CreateMoneyRequest request) {
        RegisterMemberMoneyCommand command = RegisterMemberMoneyCommand.builder()
                .membershipId(request.membershipId())
                .bankAccountId(request.bankAccountId())
                .build();
        registerMemberMoneyUseCase.registerMemberMoneyByAxon(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/member-money")
    ResponseEntity<MemberMoneyResponse> getMemberMoneyByMembershipId(@RequestParam UUID membershipId) {
        MemberMoney memberMoney = getMemberMoneyUseCase.getMemberMoneyByMembershipId(new MemberMoney.MembershipId(membershipId));
        return ResponseEntity.ok(MemberMoneyResponse.from(memberMoney));
    }

    @GetMapping("/member-money/{memberMoneyId}")
    ResponseEntity<MemberMoneyResponse> getMemberMoney(@PathVariable UUID memberMoneyId) {
        MemberMoney memberMoney = getMemberMoneyUseCase.getMemberMoney(new MemberMoney.MemberMoneyId(memberMoneyId));
        return ResponseEntity.ok(MemberMoneyResponse.from(memberMoney));
    }

    @GetMapping("/member-money/list")
    ResponseEntity<MemberMoneyListResponse> getMemberMoneyListByMembershipIds(@RequestParam List<UUID> membershipIds) {
        List<MemberMoney.MembershipId> membershipIdList = membershipIds.stream()
                .map(MemberMoney.MembershipId::new)
                .toList();
        List<MemberMoney> memberMoneyList = getMemberMoneyUseCase.getMemberMoneyList(membershipIdList);
        return ResponseEntity.ok(MemberMoneyListResponse.from(memberMoneyList));
    }
}
