package com.minipay.banking.application.port.out;

import com.minipay.banking.domain.model.FundTransaction;

public interface FundTransactionPersistencePort {
    void createFundTransaction(FundTransaction fundTransaction);
    FundTransaction readFundTransaction(FundTransaction.FundTransactionId fundTransactionId);
    void updateFundTransaction(FundTransaction fundTransaction);
}
