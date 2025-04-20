package com.minipay.money.adapter.in.web.response;

import com.minipay.money.domain.model.MemberMoney;

import java.util.List;

public record MemberMoneyListResponse(
        List<MemberMoneyResponse> memberMoneyList
) {
    public static MemberMoneyListResponse from(List<MemberMoney> memberMoneyList) {
        List<MemberMoneyResponse> memberMoneyResponses = memberMoneyList.stream()
                .map(MemberMoneyResponse::from)
                .toList();
        return new MemberMoneyListResponse(memberMoneyResponses);
    }
}
