package com.minipay.remittance.application.port.in;

public interface RequestRemittanceUseCase {
    void requestRemittance(RequestRemittanceCommand command);
}
