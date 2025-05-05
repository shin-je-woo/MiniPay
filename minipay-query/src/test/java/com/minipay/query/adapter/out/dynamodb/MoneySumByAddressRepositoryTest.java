package com.minipay.query.adapter.out.dynamodb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class MoneySumByAddressRepositoryTest {

    @Autowired
    private MoneySumByAddressRepository repository;

    @Test
    void testInsertMoneySumByAddress() {
        // given
        String address = "강남구";
        BigDecimal changeAmount = BigDecimal.valueOf(10_000);
        UUID membershipId = UUID.randomUUID();

        // when
        repository.insertMoneySumByAddress(address, changeAmount, membershipId);
    }
}