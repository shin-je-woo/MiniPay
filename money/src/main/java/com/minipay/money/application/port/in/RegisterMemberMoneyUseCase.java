package com.minipay.money.application.port.in;

import com.minipay.money.domain.MemberMoney;

public interface RegisterMemberMoneyUseCase {
    MemberMoney registerMemberMoney(RegisterMemberMoneyCommand command);
    void registerMemberMoneyAxon(RegisterMemberMoneyCommand command);
}
