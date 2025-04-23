package com.minipay.query.adapter.out.service.money;

import java.util.List;

public record MemberMoneyListResponse(
        List<MemberMoneyResponse> memberMoneyList
) {
}
