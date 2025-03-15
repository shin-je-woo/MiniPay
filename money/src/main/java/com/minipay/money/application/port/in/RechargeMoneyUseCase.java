package com.minipay.money.application.port.in;

import com.minipay.money.domain.MemberMoney;

public interface RechargeMoneyUseCase {
    MemberMoney requestMoneyRecharge(RequestMoneyRechargeCommand command);
    void rechargeMoney(RechargeMoneyCommand command);
}
