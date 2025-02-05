package com.minipay.banking.adapter.out.persistence.adapter;

import com.minipay.banking.adapter.out.persistence.entity.TransferMoneyJpaEntity;
import com.minipay.banking.adapter.out.persistence.mapper.TransferMoneyMapper;
import com.minipay.banking.adapter.out.persistence.repository.SpringDataTransferMoneyRepository;
import com.minipay.banking.application.port.out.CreateTransferMoneyPort;
import com.minipay.banking.application.port.out.ModifyTransferMoneyPort;
import com.minipay.banking.domain.TransferMoney;
import com.minipay.common.annotation.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class TransferMoneyPersistenceAdapter implements CreateTransferMoneyPort, ModifyTransferMoneyPort {

    private final SpringDataTransferMoneyRepository transferMoneyRepository;
    private final TransferMoneyMapper transferMoneyMapper;

    @Override
    public TransferMoney createTransferMoney(TransferMoney transferMoney) {
        TransferMoneyJpaEntity transferMoneyJpaEntity = transferMoneyMapper.mapToJpaEntity(transferMoney);
        transferMoneyRepository.save(transferMoneyJpaEntity);

        return transferMoneyMapper.mapToDomain(transferMoneyJpaEntity);
    }

    @Override
    public TransferMoney modifyTransferMoney(TransferMoney transferMoney) {
        TransferMoneyJpaEntity transferMoneyJpaEntity = transferMoneyMapper.mapToJpaEntity(transferMoney);
        transferMoneyRepository.save(transferMoneyJpaEntity);

        return transferMoneyMapper.mapToDomain(transferMoneyJpaEntity);
    }
}
