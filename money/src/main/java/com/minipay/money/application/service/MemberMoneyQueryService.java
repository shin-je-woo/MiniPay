package com.minipay.money.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.money.application.port.in.MemberMoneyQuery;
import com.minipay.money.application.port.out.MemberMoneyPersistencePort;
import com.minipay.money.domain.model.MemberMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberMoneyQueryService implements MemberMoneyQuery {

    private final MemberMoneyPersistencePort memberMoneyPersistencePort;

    @Override
    public MemberMoney getMemberMoney(UUID membershipId) {
        return memberMoneyPersistencePort.readMemberMoneyByMembershipId(membershipId);
    }
}
