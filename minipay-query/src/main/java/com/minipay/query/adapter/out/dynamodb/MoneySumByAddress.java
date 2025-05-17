package com.minipay.query.adapter.out.dynamodb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;

@Setter
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
public class MoneySumByAddress {

    @Getter(onMethod_ = {@DynamoDbPartitionKey, @DynamoDbAttribute("pk")})
    private String pk;

    @Getter(onMethod_ = {@DynamoDbSortKey, @DynamoDbAttribute("sk")})
    private String sk;

    @Getter(onMethod_ = {
            @DynamoDbSecondaryPartitionKey(indexNames = "address-balance-index"),
            @DynamoDbAttribute("address")
    })
    private String address;

    @Getter(onMethod_ = {
            @DynamoDbSecondarySortKey(indexNames = "address-balance-index"),
            @DynamoDbAttribute("balance")
    })
    private BigDecimal balance;
}
