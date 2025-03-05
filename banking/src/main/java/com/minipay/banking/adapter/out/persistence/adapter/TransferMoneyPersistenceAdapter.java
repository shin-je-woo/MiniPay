package com.minipay.banking.adapter.out.persistence.adapter;

import com.minipay.banking.adapter.out.persistence.entity.TransferMoneyJpaEntity;
import com.minipay.banking.adapter.out.persistence.mapper.TransferMoneyMapper;
import com.minipay.banking.adapter.out.persistence.repository.SpringDataTransferMoneyRepository;
import com.minipay.banking.application.port.out.TransferMoneyPersistencePort;
import com.minipay.banking.domain.TransferMoney;
import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class TransferMoneyPersistenceAdapter implements TransferMoneyPersistencePort {

    private final SpringDataTransferMoneyRepository transferMoneyRepository;
    private final TransferMoneyMapper transferMoneyMapper;

    @Override
    public TransferMoney createTransferMoney(TransferMoney transferMoney) {
        TransferMoneyJpaEntity transferMoneyJpaEntity = transferMoneyMapper.mapToJpaEntity(transferMoney);
        transferMoneyRepository.save(transferMoneyJpaEntity);

        return transferMoneyMapper.mapToDomain(transferMoneyJpaEntity);
    }

    @Override
    public TransferMoney updateTransferMoney(TransferMoney transferMoney) {
        TransferMoneyJpaEntity existingEntity = transferMoneyRepository.findByTransferMoneyId(transferMoney.getTransferMoneyId().value())
                .orElseThrow(() -> new DataNotFoundException("transferMoneyId not found"));

        TransferMoneyJpaEntity transferMoneyJpaEntity = transferMoneyMapper.mapToExistingJpaEntity(transferMoney, existingEntity.getId());
        transferMoneyRepository.save(transferMoneyJpaEntity);

        return transferMoneyMapper.mapToDomain(transferMoneyJpaEntity);
    }
}
