package com.minipay.money.application.port.in;

import com.minipay.money.domain.model.MemberMoney;

public interface IncreaseMoneyUseCase {
    MemberMoney increaseMoney(IncreaseMoneyCommand command);
}
