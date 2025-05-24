package com.minipay.settlement.port.out;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PaymentServicePort {
    List<PaymentInfo> getUnpaidPaymentsPaged(int page, int size);
    boolean markPaymentSettled(UUID paymentId, BigDecimal settlementAmount, BigDecimal feeAmount);
}