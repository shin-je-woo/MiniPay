package com.minipay.banking.domain;

import com.minipay.common.DomainRuleException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferMoney {

    private final TransferMoneyId transferMoneyId;
    private final FirmBankingAccount fromFirmBankingAccount;
    private final FirmBankingAccount toFirmBankingAccount;
    private final MoneyAmount moneyAmount;
    private final TransferMoneyStatus status;

    public static TransferMoney create(
            FirmBankingAccount fromAccount,
            FirmBankingAccount toAccount,
            MoneyAmount moneyAmount
    ) {
        return new TransferMoney(null, fromAccount, toAccount, moneyAmount, TransferMoneyStatus.REQUESTED);
    }

    public static TransferMoney withId(
            TransferMoneyId transferMoneyId,
            FirmBankingAccount fromAccount,
            FirmBankingAccount toAccount,
            MoneyAmount moneyAmount,
            TransferMoneyStatus status
    ) {
        return new TransferMoney(transferMoneyId, fromAccount, toAccount, moneyAmount, status);
    }

    public TransferMoney changeStatus(TransferMoneyStatus status) {
        if (status == null) {
            throw new DomainRuleException("status can't be null");
        }
        return new TransferMoney(this.transferMoneyId, this.fromFirmBankingAccount, this.toFirmBankingAccount, this.moneyAmount, status);
    }

    public record TransferMoneyId(Long value) {
        public TransferMoneyId {
            if (value == null) {
                throw new DomainRuleException("transfer money id is null");
            }
        }
    }

    public record FirmBankingAccount(String bankName, String bankAccountNumber) {
        public FirmBankingAccount {
            if (!StringUtils.hasText(bankName) || !StringUtils.hasText(bankAccountNumber)) {
                throw new DomainRuleException("firm banking account is empty");
            }
            if (!bankAccountNumber.matches("\\d{10,16}")) {
                throw new DomainRuleException("invalid bank account number");
            }
        }
    }

    public record MoneyAmount(BigDecimal value) {
        public MoneyAmount {
            if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
                throw new DomainRuleException("money amount is invalid");
            }
        }
    }

    public enum TransferMoneyStatus {
        REQUESTED,
        SUCCESS,
        FAILED
    }
}
