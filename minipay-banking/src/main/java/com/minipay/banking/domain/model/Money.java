package com.minipay.banking.domain.model;

import com.minipay.common.exception.DomainRuleException;

import java.math.BigDecimal;

public record Money(BigDecimal value) {

    public Money {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainRuleException("money is less than zero");
        }
    }

    public static Money ZERO = new Money(BigDecimal.ZERO);

    public Money add(Money money) {
        return new Money(this.value.add(money.value));
    }

    public Money subtract(Money money) {
        return new Money(this.value.subtract(money.value));
    }

    public boolean isNegative() {
        return this.value.compareTo(BigDecimal.ZERO) < 0;
    }
}
