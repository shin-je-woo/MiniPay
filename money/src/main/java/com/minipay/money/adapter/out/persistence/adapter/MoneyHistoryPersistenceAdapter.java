package com.minipay.money.adapter.out.persistence.adapter;

import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.common.exception.DataNotFoundException;
import com.minipay.money.adapter.out.persistence.entity.MoneyHistoryJpaEntity;
import com.minipay.money.adapter.out.persistence.mapper.MoneyHistoryMapper;
import com.minipay.money.adapter.out.persistence.repository.SpringDataMoneyHistoryRepository;
import com.minipay.money.application.port.out.CreateMoneyHistoryPort;
import com.minipay.money.application.port.out.MoneyHistoryPersistencePort;
import com.minipay.money.domain.MoneyHistory;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyHistoryPersistenceAdapter implements CreateMoneyHistoryPort, MoneyHistoryPersistencePort {

    private final SpringDataMoneyHistoryRepository moneyHistoryRepository;
    private final MoneyHistoryMapper moneyHistoryMapper;

    @Override
    public void createMoneyHistory(MoneyHistory moneyHistory) {
        MoneyHistoryJpaEntity moneyHistoryJpaEntity = moneyHistoryMapper.mapToJpaEntity(moneyHistory);
        moneyHistoryRepository.save(moneyHistoryJpaEntity);
    }

    @Override
    public MoneyHistory loadMoneyHistory(MoneyHistory.MoneyHistoryId moneyHistoryId) {
        return moneyHistoryRepository.findByMoneyHistoryId(moneyHistoryId.value())
                .map(moneyHistoryMapper::mapToDomain)
                .orElseThrow(() -> new DataNotFoundException("MoneyHistory not found"));
    }

    @Override
    public void updateMoneyHistory(MoneyHistory moneyHistory) {
        MoneyHistoryJpaEntity existingEntity = moneyHistoryRepository.findByMoneyHistoryId(moneyHistory.getMoneyHistoryId().value())
                .orElseThrow(() -> new DataNotFoundException("MoneyHistory not found"));

        MoneyHistoryJpaEntity moneyHistoryJpaEntity = moneyHistoryMapper.mapToExistingJpaEntity(moneyHistory, existingEntity.getId());
        moneyHistoryRepository.save(moneyHistoryJpaEntity);
    }
}
