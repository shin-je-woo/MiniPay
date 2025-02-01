package com.minipay.money.application.port.out;

import com.minipay.money.domain.MemberMoney;

public interface LoadMemberMoneyPort {
    MemberMoney loadMemberMoney(MemberMoney.MemberMoneyId memberMoneyId);
}
