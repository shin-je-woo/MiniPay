package com.minipay.money.adapter.out.persistence.adapter;

import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.money.adapter.out.persistence.entity.MoneyHistoryJpaEntity;
import com.minipay.money.adapter.out.persistence.mapper.MoneyHistoryMapper;
import com.minipay.money.adapter.out.persistence.repository.SpringDataMoneyHistoryRepository;
import com.minipay.money.application.port.out.CreateMoneyHistoryPort;
import com.minipay.money.domain.MoneyHistory;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyHistoryPersistenceAdapter implements CreateMoneyHistoryPort {

    private final SpringDataMoneyHistoryRepository moneyHistoryRepository;
    private final MoneyHistoryMapper moneyHistoryMapper;

    @Override
    public void createMoneyHistory(MoneyHistory moneyHistory) {
        MoneyHistoryJpaEntity moneyHistoryJpaEntity = moneyHistoryMapper.mapToJpaEntity(moneyHistory);
        moneyHistoryRepository.save(moneyHistoryJpaEntity);
    }
}
