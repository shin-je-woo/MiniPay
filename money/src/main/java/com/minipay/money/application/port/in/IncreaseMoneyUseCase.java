package com.minipay.money.application.port.in;

import com.minipay.money.domain.MemberMoney;

public interface IncreaseMoneyUseCase {
    MemberMoney increaseMoney(IncreaseMoneyCommand command);
}
