package com.minipay.query.adapter.out.dynamodb;

import com.minipay.query.application.port.out.InsertMoneySumPort;
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
public class MoneySumDynamodbAdapter implements InsertMoneySumPort {

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

        updateOrInsertAmount(pk, sk, changeAmount);
    }

    @Override
    public void upsertTotalSummary(String address, BigDecimal amount) {
        String pk = String.format("ADDR#%s", address);
        String sk = "TOTAL";

        updateOrInsertAmount(pk, sk, amount);
    }

    private void updateOrInsertAmount(String pk, String sk, BigDecimal amount) {
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
}
