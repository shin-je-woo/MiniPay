package com.minipay.money.application.port.out;

import com.minipay.money.domain.MemberMoney;

public interface MemberMoneyPersistencePort {
    MemberMoney createMemberMoney(MemberMoney memberMoney);
    void updateMemberMoney(MemberMoney memberMoney);
    MemberMoney readMemberMoney(MemberMoney.MemberMoneyId memberMoneyId);
}
