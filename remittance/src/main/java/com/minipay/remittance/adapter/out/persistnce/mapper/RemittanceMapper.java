package com.minipay.remittance.adapter.out.persistnce.mapper;

import com.minipay.common.annotation.DomainMapper;
import com.minipay.remittance.adapter.out.persistnce.entity.RemittanceJpaEntity;
import com.minipay.remittance.domain.Remittance;

@DomainMapper
public class RemittanceMapper {

    public RemittanceJpaEntity mapToJpaEntity(Remittance remittance) {
        return RemittanceJpaEntity.builder()
                .remittanceId(remittance.getRemittanceId().value())
                .senderId(remittance.getSender().membershipId())
                .recipientId(remittance.getRecipient().membershipId())
                .destBankName(remittance.getRecipient().bankName())
                .destBankName(remittance.getRecipient().accountNumber())
                .remittanceType(remittance.getRemittanceType())
                .remittanceStatus(remittance.getRemittanceStatus())
                .amount(remittance.getAmount().value())
                .build();
    }

    public RemittanceJpaEntity mapToExistingJpaEntity(Remittance remittance, Long jpaEntityId) {
        return RemittanceJpaEntity.builder()
                .id(jpaEntityId)
                .remittanceId(remittance.getRemittanceId().value())
                .senderId(remittance.getSender().membershipId())
                .recipientId(remittance.getRecipient().membershipId())
                .destBankName(remittance.getRecipient().bankName())
                .destBankAccountNumber(remittance.getRecipient().accountNumber())
                .remittanceType(remittance.getRemittanceType())
                .remittanceStatus(remittance.getRemittanceStatus())
                .amount(remittance.getAmount().value())
                .build();
    }
}
