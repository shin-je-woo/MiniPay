package com.minipay.money.application.port.out;

import com.minipay.money.domain.model.MemberMoney;

import java.util.List;
import java.util.UUID;

public interface MemberMoneyPersistencePort {
    MemberMoney createMemberMoney(MemberMoney memberMoney);
    void updateMemberMoney(MemberMoney memberMoney);
    MemberMoney readMemberMoney(MemberMoney.MemberMoneyId memberMoneyId);
    MemberMoney readMemberMoneyByMembershipId(UUID membershipId);
    MemberMoney readMemberMoneyByBankAccountId(UUID bankAccountId);
    List<MemberMoney> readMemberMoneyListByMembershipIds(List<UUID> membershipIds);
}
