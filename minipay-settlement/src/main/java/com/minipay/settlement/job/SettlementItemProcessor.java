package com.minipay.settlement.job;

import com.minipay.settlement.port.out.*;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component(SettlementJobProvider.JOB_NAME + "Processor")
@StepScope
@RequiredArgsConstructor
public class SettlementItemProcessor implements ItemProcessor<PaymentInfo, SettlementResult> {

    private final MoneyServicePort moneyServicePort;
    private final BankingServicePort bankingServicePort;

    @Override
    public SettlementResult process(@Nonnull PaymentInfo paymentInfo) {
        MoneyInfo moneyInfo = moneyServicePort.getMoneyInfoBySellerId(paymentInfo.sellerId());
        BankAccountInfo bankAccountInfo = bankingServicePort.getBankAccountInfo(moneyInfo.bankAccountId());
        return SettlementResult.withFeeApplied(bankAccountInfo, paymentInfo);
    }
}
