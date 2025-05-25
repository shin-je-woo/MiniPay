package com.minipay.settlement.port.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PaymentServicePort {
    List<PaymentInfo> getUnpaidPaymentsPaged(int page, int size, LocalDate fromDate, LocalDate toDate);
    boolean markPaymentSettled(UUID paymentId, BigDecimal settlementAmount, BigDecimal feeAmount);
}