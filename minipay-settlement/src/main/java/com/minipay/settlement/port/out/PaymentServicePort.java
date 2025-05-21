package com.minipay.settlement.port.out;

import java.util.List;

public interface PaymentServicePort {
    List<PaymentInfo> getUnpaidPaymentsPaged(int page, int size);
}
