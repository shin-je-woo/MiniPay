package com.minipay.query.application.port.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface InsertMoneySumPort {
    void insertMoneyChange(String address, BigDecimal changeAmount, LocalDate changeDate, UUID membershipId);
    void upsertDailySummary(String address, BigDecimal changeAmount, LocalDate changeDate);
    void upsertTotalSummary(String address, BigDecimal amount);
}
