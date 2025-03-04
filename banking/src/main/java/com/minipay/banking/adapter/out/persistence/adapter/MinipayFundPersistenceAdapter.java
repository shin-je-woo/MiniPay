package com.minipay.banking.adapter.out.persistence.adapter;

import com.minipay.banking.adapter.out.persistence.entity.MinipayFundJpaEntity;
import com.minipay.banking.adapter.out.persistence.mapper.MinipayFundMapper;
import com.minipay.banking.adapter.out.persistence.repository.SpringDataMinipayFundRepository;
import com.minipay.banking.application.port.out.MinipayFundPersistencePort;
import com.minipay.banking.domain.MinipayFund;
import com.minipay.common.annotation.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MinipayFundPersistenceAdapter implements MinipayFundPersistencePort {

    private final MinipayFundMapper minipayFundMapper;
    private final SpringDataMinipayFundRepository minipayFundRepository;

    @Override
    public void createMinipayFund(MinipayFund minipayFund) {
        MinipayFundJpaEntity minipayFundJpaEntity = minipayFundMapper.mapToJpaEntity(minipayFund);
        minipayFundRepository.save(minipayFundJpaEntity);
    }
}
