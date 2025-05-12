package com.minipay.money.application.port.in;

import com.minipay.money.domain.model.MemberMoney;

import java.util.List;

public interface GetMemberMoneyUseCase {
    MemberMoney getMemberMoney(MemberMoney.MemberMoneyId memberMoneyId);
    MemberMoney getMemberMoneyByMembershipId(MemberMoney.MembershipId membershipId);
    List<MemberMoney> getMemberMoneyList(List<MemberMoney.MembershipId> membershipIds);
}
