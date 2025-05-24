package com.minipay.settlement.job;

import com.minipay.settlement.port.out.BankAccountInfo;
import com.minipay.settlement.port.out.PaymentInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public record SettlementResult(
        UUID bankAccountId,
        UUID paymentId,
        String bankName,
        String bankAccountNumber,
        BigDecimal settlementAmount,
        BigDecimal feeAmount
) {
    public static SettlementResult withFeeApplied(BankAccountInfo bankAccountInfo, PaymentInfo paymentInfo) {
        BigDecimal sellerRate = BigDecimal.valueOf(100)
                .subtract(paymentInfo.feeRate())
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
        BigDecimal settlementAmount = paymentInfo.price()
                .multiply(sellerRate)
                .setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal feeAmount = paymentInfo.price()
                .subtract(settlementAmount);

        return new SettlementResult(
                bankAccountInfo.bankAccountId(),
                paymentInfo.paymentId(),
                bankAccountInfo.bankName(),
                bankAccountInfo.accountNumber(),
                settlementAmount,
                feeAmount
        );
    }
}
