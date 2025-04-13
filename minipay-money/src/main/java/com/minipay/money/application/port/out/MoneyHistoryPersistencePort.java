package com.minipay.money.application.port.out;

import com.minipay.money.domain.model.MoneyHistory;

public interface MoneyHistoryPersistencePort {
    void createMoneyHistory(MoneyHistory moneyHistory);
    MoneyHistory readMoneyHistory(MoneyHistory.MoneyHistoryId moneyHistoryId);
    void updateMoneyHistory(MoneyHistory moneyHistory);
}
