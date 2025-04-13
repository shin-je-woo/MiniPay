package com.minipay.money.application.port.out;

import com.minipay.money.domain.model.MemberMoney;

import java.util.UUID;

public interface MemberMoneyPersistencePort {
    MemberMoney createMemberMoney(MemberMoney memberMoney);
    void updateMemberMoney(MemberMoney memberMoney);
    MemberMoney readMemberMoney(MemberMoney.MemberMoneyId memberMoneyId);
    MemberMoney readMemberMoneyByMembershipId(UUID membershipId);
    MemberMoney readMemberMoneyByBankAccountId(UUID bankAccountId);
}
