package com.minipay.common.event;

import lombok.Getter;

@Getter
public class EventType {
    public static final String LOGGING = "common.log.access";
    public static final String BANK_ACCOUNT_CREATED = "banking.bank-account.created";
}
