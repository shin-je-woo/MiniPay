package com.minipay.remittance.application.port.out;

import com.minipay.remittance.domain.Remittance;

public interface RemittancePersistencePort {
    void createRemittance(Remittance remittance);
    void updateRemittance(Remittance remittance);
}
