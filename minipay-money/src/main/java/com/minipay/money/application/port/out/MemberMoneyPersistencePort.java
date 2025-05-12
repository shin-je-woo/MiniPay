package com.minipay.money.application.port.out;

import com.minipay.money.domain.model.MemberMoney;

import java.util.List;

public interface MemberMoneyPersistencePort {
    MemberMoney createMemberMoney(MemberMoney memberMoney);
    void updateMemberMoney(MemberMoney memberMoney);
    MemberMoney readMemberMoney(MemberMoney.MemberMoneyId memberMoneyId);
    MemberMoney readMemberMoneyByMembershipId(MemberMoney.MembershipId membershipId);
    MemberMoney readMemberMoneyByBankAccountId(MemberMoney.BankAccountId bankAccountId);
    List<MemberMoney> readMemberMoneyListByMembershipIds(List<MemberMoney.MembershipId> membershipIds);
}
