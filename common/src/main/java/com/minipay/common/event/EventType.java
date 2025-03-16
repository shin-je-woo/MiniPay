package com.minipay.common.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {
    BANK_ACCOUNT_CREATED,
    MEMBER_MONEY_RECHARGE_REQUESTED,
    MINIPAY_FUND_DEPOSITED,
    MINIPAY_FUND_WITHDRAWN
}
