package com.minipay.banking.adapter.out.persistence.mapper;

import com.minipay.banking.adapter.out.persistence.entity.TransferMoneyJpaEntity;
import com.minipay.banking.domain.TransferMoney;
import com.minipay.common.DomainMapper;

@DomainMapper
public class TransferMoneyMapper {

    public TransferMoney mapToDomain(TransferMoneyJpaEntity transferMoney) {
        return TransferMoney.withId(
                new TransferMoney.TransferMoneyId(transferMoney.getId()),
                new TransferMoney.FirmBankingAccount(transferMoney.getFromBankName(), transferMoney.getFromBankAccountNumber()),
                new TransferMoney.FirmBankingAccount(transferMoney.getToBankName(), transferMoney.getToBankAccountNumber()),
                new TransferMoney.MoneyAmount(transferMoney.getMoneyAmount()),
                transferMoney.getStatus()
        );
    }

    public TransferMoneyJpaEntity mapToJpaEntity(TransferMoney transferMoney) {
        return TransferMoneyJpaEntity.builder()
                .id(transferMoney.getTransferMoneyId() == null ? null : transferMoney.getTransferMoneyId().value())
                .fromBankName(transferMoney.getFromFirmBankingAccount().bankName())
                .fromBankAccountNumber(transferMoney.getFromFirmBankingAccount().bankAccountNumber())
                .toBankName(transferMoney.getToFirmBankingAccount().bankName())
                .toBankAccountNumber(transferMoney.getToFirmBankingAccount().bankAccountNumber())
                .moneyAmount(transferMoney.getMoneyAmount().value())
                .status(transferMoney.getStatus())
                .build();
    }
}
