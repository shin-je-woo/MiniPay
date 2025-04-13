package com.minipay.remittance.adapter.out.persistnce.adapter;

import com.minipay.common.annotation.PersistenceAdapter;
import com.minipay.common.exception.DataNotFoundException;
import com.minipay.remittance.adapter.out.persistnce.entity.RemittanceJpaEntity;
import com.minipay.remittance.adapter.out.persistnce.mapper.RemittanceMapper;
import com.minipay.remittance.adapter.out.persistnce.repository.SpringDataRemittanceRepository;
import com.minipay.remittance.application.port.out.RemittancePersistencePort;
import com.minipay.remittance.domain.Remittance;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RemittancePersistenceAdapter implements RemittancePersistencePort {

    private final SpringDataRemittanceRepository remittanceRepository;
    private final RemittanceMapper remittanceMapper;

    @Override
    public void createRemittance(Remittance remittance) {
        RemittanceJpaEntity remittanceJpaEntity = remittanceMapper.mapToJpaEntity(remittance);
        remittanceRepository.save(remittanceJpaEntity);
    }

    @Override
    public void updateRemittance(Remittance remittance) {
        RemittanceJpaEntity existingEntity = remittanceRepository.findByRemittanceId(remittance.getRemittanceId().value())
                .orElseThrow(() -> new DataNotFoundException("Remittance not found"));

        RemittanceJpaEntity remittanceJpaEntity = remittanceMapper.mapToExistingJpaEntity(remittance, existingEntity.getId());
        remittanceRepository.save(remittanceJpaEntity);
    }
}
