package com.minipay.money.application.port.in;

import com.minipay.money.domain.MemberMoney;

import java.util.UUID;

public interface MemberMoneyQuery {
    MemberMoney getMemberMoney(UUID membershipId);
}
