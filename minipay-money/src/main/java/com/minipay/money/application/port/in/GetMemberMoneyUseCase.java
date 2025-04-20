package com.minipay.money.application.port.in;

import com.minipay.money.domain.model.MemberMoney;

import java.util.List;
import java.util.UUID;

public interface GetMemberMoneyUseCase {
    MemberMoney getMemberMoney(UUID membershipId);
    List<MemberMoney> getMemberMoneyList(List<UUID> membershipIds);
}
