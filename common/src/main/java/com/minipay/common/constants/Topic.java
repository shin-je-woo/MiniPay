package com.minipay.common.constants;

import lombok.Getter;

@Getter
public class Topic {
    public static final String UNKNOWN = "unknown";
    public static final String BANK_ACCOUNT_EVENTS = "banking.bank-account.events";
    public static final String MEMBER_MONEY_EVENTS = "money.member-money.events";
    public static final String FUND_TRANSACTION_EVENTS = "banking.fund-transaction.events";
}
