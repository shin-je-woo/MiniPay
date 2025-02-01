package com.minipay.money.application.port.out;

import com.minipay.money.domain.MemberMoney;

public interface CreateMemberMoneyPort {
    MemberMoney createMemberMoney(MemberMoney memberMoney);
}
