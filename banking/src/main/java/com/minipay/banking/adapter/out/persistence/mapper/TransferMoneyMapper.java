package com.minipay.banking.adapter.out.persistence.mapper;

import com.minipay.banking.adapter.out.persistence.entity.TransferMoneyJpaEntity;
import com.minipay.banking.domain.ExternalBankAccount;
import com.minipay.banking.domain.Money;
import com.minipay.banking.domain.TransferMoney;
import com.minipay.common.annotation.DomainMapper;

@DomainMapper
public class TransferMoneyMapper {

    public TransferMoney mapToDomain(TransferMoneyJpaEntity transferMoney) {
        return TransferMoney.withId(
                new TransferMoney.TransferMoneyId(transferMoney.getTransferMoneyId()),
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(transferMoney.getSrcBankName()),
                        new ExternalBankAccount.AccountNumber(transferMoney.getSrcAccountNumber())
                ),
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(transferMoney.getDestBankName()),
                        new ExternalBankAccount.AccountNumber(transferMoney.getDestAccountNumber())
                ),
                new Money(transferMoney.getAmount()),
                transferMoney.getStatus()
        );
    }

    public TransferMoneyJpaEntity mapToJpaEntity(TransferMoney transferMoney) {
        return TransferMoneyJpaEntity.builder()
                .transferMoneyId(transferMoney.getTransferMoneyId().value())
                .srcBankName(transferMoney.getSourceAccount().bankName().value())
                .srcAccountNumber(transferMoney.getSourceAccount().accountNumber().value())
                .destBankName(transferMoney.getDestinationAccount().bankName().value())
                .destAccountNumber(transferMoney.getDestinationAccount().accountNumber().value())
                .amount(transferMoney.getAmount().value())
                .status(transferMoney.getStatus())
                .build();
    }

    public TransferMoneyJpaEntity mapToExistingJpaEntity(TransferMoney transferMoney, Long jpaEntityId) {
        return TransferMoneyJpaEntity.builder()
                .id(jpaEntityId)
                .transferMoneyId(transferMoney.getTransferMoneyId().value())
                .srcBankName(transferMoney.getSourceAccount().bankName().value())
                .srcAccountNumber(transferMoney.getSourceAccount().accountNumber().value())
                .destBankName(transferMoney.getDestinationAccount().bankName().value())
                .destAccountNumber(transferMoney.getDestinationAccount().accountNumber().value())
                .amount(transferMoney.getAmount().value())
                .status(transferMoney.getStatus())
                .build();
    }
}
