package com.minipay.banking.adapter.out.persistence.adapter;

import com.minipay.banking.adapter.out.persistence.entity.FundTransactionJpaEntity;
import com.minipay.banking.adapter.out.persistence.mapper.FundTransactionMapper;
import com.minipay.banking.adapter.out.persistence.repository.SpringDataFundTransactionRepository;
import com.minipay.banking.application.port.out.FundTransactionPersistencePort;
import com.minipay.banking.domain.model.FundTransaction;
import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class FundTransactionPersistenceAdapter implements FundTransactionPersistencePort {

    private final FundTransactionMapper fundTransactionMapper;
    private final SpringDataFundTransactionRepository fundTransactionRepository;

    @Override
    public void createFundTransaction(FundTransaction fundTransaction) {
        FundTransactionJpaEntity fundTransactionJpaEntity = fundTransactionMapper.mapToJpaEntity(fundTransaction);
        fundTransactionRepository.save(fundTransactionJpaEntity);
    }

    @Override
    public FundTransaction readFundTransaction(FundTransaction.FundTransactionId fundTransactionId) {
        return fundTransactionRepository.findByFundTransactionId(fundTransactionId.value())
                .map(fundTransactionMapper::mapToDomain)
                .orElseThrow(() -> new DataNotFoundException("FundTransaction not found"));
    }

    @Override
    public void updateFundTransaction(FundTransaction fundTransaction) {
        FundTransactionJpaEntity existingEntity = fundTransactionRepository.findByFundTransactionId(fundTransaction.getFundTransactionId().value())
                .orElseThrow(() -> new DataNotFoundException("FundTransaction not found"));

        FundTransactionJpaEntity fundTransactionJpaEntity = fundTransactionMapper.mapToExistingJpaEntity(fundTransaction, existingEntity.getId());
        fundTransactionRepository.save(fundTransactionJpaEntity);
    }
}
