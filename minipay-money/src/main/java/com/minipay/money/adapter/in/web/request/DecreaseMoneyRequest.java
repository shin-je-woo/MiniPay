package com.minipay.money.adapter.in.web.request;

import java.math.BigDecimal;

public record DecreaseMoneyRequest(
        BigDecimal amount
) {
}
