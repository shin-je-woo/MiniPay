package com.minipay.query.adapter.in.web.controller;

import com.minipay.common.annotation.WebAdapter;
import com.minipay.query.application.port.in.GetMoneySumByAddressQuery;
import com.minipay.query.application.port.in.GetMoneySumAggregationUseCase;
import com.minipay.query.application.port.in.GetMoneySumQueryUseCase;
import com.minipay.query.domain.MoneySumByRegion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class MoneySumController {

    private final GetMoneySumAggregationUseCase getMoneySumAggregationUseCase;
    private final GetMoneySumQueryUseCase getMoneySumQueryUseCase;

    @GetMapping("/aggregation/money-sum")
    ResponseEntity<BigDecimal> getMoneySumAggregation(@RequestParam String address) {
        GetMoneySumByAddressQuery query = new GetMoneySumByAddressQuery(address);
        BigDecimal moneySum = getMoneySumAggregationUseCase.getMoneySumByAddress(query);
        return ResponseEntity.ok(moneySum);
    }

    @GetMapping("/query/money-sum")
    ResponseEntity<BigDecimal> getMoneySumQuery(@RequestParam String address) {
        GetMoneySumByAddressQuery query = new GetMoneySumByAddressQuery(address);
        MoneySumByRegion moneySumByRegion = getMoneySumQueryUseCase.getMoneySumByAddress(query);
        return ResponseEntity.ok(moneySumByRegion.getMoneySum().value());
    }
}
