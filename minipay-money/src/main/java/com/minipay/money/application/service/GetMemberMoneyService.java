package com.minipay.money.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.money.application.port.in.GetMemberMoneyUseCase;
import com.minipay.money.application.port.out.MemberMoneyPersistencePort;
import com.minipay.money.domain.model.MemberMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetMemberMoneyService implements GetMemberMoneyUseCase {

    private final MemberMoneyPersistencePort memberMoneyPersistencePort;

    @Override
    public MemberMoney getMemberMoney(MemberMoney.MemberMoneyId memberMoneyId) {
        return memberMoneyPersistencePort.readMemberMoney(memberMoneyId);
    }

    @Override
    public MemberMoney getMemberMoneyByMembershipId(MemberMoney.MembershipId membershipId) {
        return memberMoneyPersistencePort.readMemberMoneyByMembershipId(membershipId);
    }

    @Override
    public List<MemberMoney> getMemberMoneyList(List<MemberMoney.MembershipId> membershipIds) {
        return memberMoneyPersistencePort.readMemberMoneyListByMembershipIds(membershipIds);
    }
}
