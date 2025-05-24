package com.minipay.settlement.port.out;

import java.util.UUID;

public interface MoneyServicePort {
    MoneyInfo getMoneyInfoBySellerId(UUID sellerId);
}