package com.minipay.query.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.query.application.port.in.GetDailyMoneySumByAddressQuery;
import com.minipay.query.application.port.in.GetMoneySumByAddressQuery;
import com.minipay.query.application.port.in.GetMoneySumQueryUseCase;
import com.minipay.query.domain.MoneySumByRegion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class MoneySumQueryController {

    private final GetMoneySumQueryUseCase getMoneySumQueryUseCase;

    @GetMapping("/query/money-sum")
    ResponseEntity<BigDecimal> getMoneySumQuery(@RequestParam String address) {
        GetMoneySumByAddressQuery query = new GetMoneySumByAddressQuery(address);
        MoneySumByRegion moneySumByRegion = getMoneySumQueryUseCase.getMoneySumByAddress(query);
        return ResponseEntity.ok(moneySumByRegion.getMoneySum().value());
    }

    @GetMapping("/query/daily-money-sum")
    ResponseEntity<BigDecimal> getDailyMoneySumQuery(@RequestParam String address, @RequestParam LocalDate date) {
        GetDailyMoneySumByAddressQuery query = new GetDailyMoneySumByAddressQuery(address, date);
        MoneySumByRegion moneySumByRegion = getMoneySumQueryUseCase.getDailyMoneySumByAddress(query);
        return ResponseEntity.ok(moneySumByRegion.getMoneySum().value());
    }

}
