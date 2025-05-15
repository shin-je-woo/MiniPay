package com.minipay.query.adapter.out.dynamodb;

import com.minipay.query.adapter.in.axon.dto.QueryDailyMoneySumByAddress;
import com.minipay.query.adapter.in.axon.dto.QueryMoneySumByAddress;
import com.minipay.query.application.port.out.GetMoneySumPort;
import com.minipay.query.application.port.out.InsertMoneySumPort;
import com.minipay.query.domain.MoneySumByRegion;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
public class MoneySumDynamodbAdapter implements InsertMoneySumPort, GetMoneySumPort {

    private final DynamoDbTable<MoneySumByAddress> table;

    public MoneySumDynamodbAdapter(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("money_sum_by_address", TableSchema.fromBean(MoneySumByAddress.class));
    }

    @Override
    public void insertMoneyChange(String address, BigDecimal changeAmount, LocalDate changeDate, UUID membershipId) {
        String pk = String.format("ADDR#%s#DATE#%s", address, changeDate);
        String sk = membershipId.toString();

        MoneySumByAddress moneySumByAddress = new MoneySumByAddress(pk, sk, changeAmount);
        table.putItem(moneySumByAddress);
    }

    @Override
    public void upsertDailySummary(String address, BigDecimal changeAmount, LocalDate changeDate) {
        String pk = String.format("ADDR#%s#DATE#%s", address, changeDate);
        String sk = "SUMMARY";

        upsertAmount(pk, sk, changeAmount);
    }

    @Override
    public void upsertTotalSummary(String address, BigDecimal amount) {
        String pk = String.format("ADDR#%s", address);
        String sk = "TOTAL";

        upsertAmount(pk, sk, amount);
    }

    @Override
    public MoneySumByRegion getMoneySumByAddress(String address) {
        String pk = String.format("ADDR#%s", address);
        String sk = "TOTAL";

        Key key = Key.builder()
                .partitionValue(pk)
                .sortValue(sk)
                .build();

        return getMoneySumByRegion(key, address);
    }

    @Override
    public MoneySumByRegion getDailyMoneySumByAddress(String address, LocalDate date) {
        String pk = String.format("ADDR#%s#DATE#%s", address, date);
        String sk = "SUMMARY";

        Key key = Key.builder()
                .partitionValue(pk)
                .sortValue(sk)
                .build();

        return getMoneySumByRegion(key, address);
    }

    @QueryHandler
    public MoneySumByRegion query(QueryMoneySumByAddress query) {
        return getMoneySumByAddress(query.address());
    }

    @QueryHandler
    public MoneySumByRegion query(QueryDailyMoneySumByAddress query) {
        return getDailyMoneySumByAddress(query.address(), query.date());
    }

    private void upsertAmount(String pk, String sk, BigDecimal amount) {
        Key key = Key.builder().partitionValue(pk).sortValue(sk).build();

        Optional.ofNullable(table.getItem(r -> r.key(key)))
                .ifPresentOrElse(
                        existing -> {
                            BigDecimal newBalance = existing.getBalance().add(amount);
                            existing.setBalance(newBalance);
                            table.updateItem(existing);
                        },
                        () -> {
                            MoneySumByAddress newItem = new MoneySumByAddress(pk, sk, amount);
                            table.putItem(newItem);
                        }
                );
    }

    private MoneySumByRegion getMoneySumByRegion(Key key, String address) {
        return Optional.ofNullable(table.getItem(r -> r.key(key)))
                .map(moneySumByAddress -> MoneySumByRegion.newInstance(
                        new MoneySumByRegion.RegionName(address),
                        new MoneySumByRegion.MoneySum(moneySumByAddress.getBalance())
                ))
                .orElseThrow(() -> new IllegalStateException("Query 데이터가 없습니다."));
    }
}
