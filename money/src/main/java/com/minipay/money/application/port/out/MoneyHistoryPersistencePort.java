package com.minipay.money.application.port.out;

import com.minipay.money.domain.MoneyHistory;

public interface MoneyHistoryPersistencePort {
    MoneyHistory loadMoneyHistory(MoneyHistory.MoneyHistoryId moneyHistoryId);
    void updateMoneyHistory(MoneyHistory moneyHistory);
}
