package com.minipay.money.application.port.out;

import com.minipay.money.domain.MoneyHistory;

public interface CreateMoneyHistoryPort {
    void createMoneyHistory(MoneyHistory moneyHistory);
}
