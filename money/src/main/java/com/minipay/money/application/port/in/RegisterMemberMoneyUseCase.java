package com.minipay.money.application.port.in;

import com.minipay.money.domain.model.MemberMoney;

public interface RegisterMemberMoneyUseCase {
    MemberMoney registerMemberMoney(RegisterMemberMoneyCommand command);
    void registerMemberMoneyByAxon(RegisterMemberMoneyCommand command);
}
