package com.minipay.banking.adapter.out.persistence.mapper;

import com.minipay.banking.adapter.out.persistence.entity.FundTransactionJpaEntity;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.ExternalBankAccount;
import com.minipay.banking.domain.model.FundTransaction;
import com.minipay.banking.domain.model.Money;
import com.minipay.common.annotation.DomainMapper;
import org.springframework.util.StringUtils;

@DomainMapper
public class FundTransactionMapper {

    public FundTransaction mapToDomain(FundTransactionJpaEntity fundTransaction) {
        return FundTransaction.withId(
                new FundTransaction.FundTransactionId(fundTransaction.getFundTransactionId()),
                new BankAccount.BankAccountId(fundTransaction.getBankAccountId()),
                fundTransaction.getMinipayBankAccount(),
                mapExternalBankAccount(fundTransaction),
                fundTransaction.getFundType(),
                new Money(fundTransaction.getAmount()),
                fundTransaction.getStatus()
        );
    }

    public FundTransactionJpaEntity mapToJpaEntity(FundTransaction fundTransaction) {
        return FundTransactionJpaEntity.builder()
                .fundTransactionId(fundTransaction.getFundTransactionId().value())
                .bankAccountId(fundTransaction.getBankAccountId().value())
                .minipayBankAccount(fundTransaction.getMinipayBankAccount())
                .withdrawalBankName(fundTransaction.getWithdrawalBankAccount() != null ? fundTransaction.getWithdrawalBankAccount().bankName().value() : null)
                .withdrawalAccountNumber(fundTransaction.getWithdrawalBankAccount() != null ? fundTransaction.getWithdrawalBankAccount().accountNumber().value() : null)
                .fundType(fundTransaction.getFundType())
                .status(fundTransaction.getStatus())
                .amount(fundTransaction.getAmount().value())
                .build();
    }

    public FundTransactionJpaEntity mapToExistingJpaEntity(FundTransaction fundTransaction, Long jpaEntityId) {
        return FundTransactionJpaEntity.builder()
                .id(jpaEntityId)
                .fundTransactionId(fundTransaction.getFundTransactionId().value())
                .bankAccountId(fundTransaction.getBankAccountId().value())
                .minipayBankAccount(fundTransaction.getMinipayBankAccount())
                .withdrawalBankName(fundTransaction.getWithdrawalBankAccount() != null ? fundTransaction.getWithdrawalBankAccount().bankName().value() : null)
                .withdrawalAccountNumber(fundTransaction.getWithdrawalBankAccount() != null ? fundTransaction.getWithdrawalBankAccount().accountNumber().value() : null)
                .fundType(fundTransaction.getFundType())
                .status(fundTransaction.getStatus())
                .amount(fundTransaction.getAmount().value())
                .build();
    }

    private ExternalBankAccount mapExternalBankAccount(FundTransactionJpaEntity fundTransaction) {
        if (!StringUtils.hasText(fundTransaction.getWithdrawalBankName()) || !StringUtils.hasText(fundTransaction.getWithdrawalAccountNumber())) {
            return null;
        }

        return new ExternalBankAccount(
                new ExternalBankAccount.BankName(fundTransaction.getWithdrawalBankName()),
                new ExternalBankAccount.AccountNumber(fundTransaction.getWithdrawalAccountNumber())
        );
    }
}
