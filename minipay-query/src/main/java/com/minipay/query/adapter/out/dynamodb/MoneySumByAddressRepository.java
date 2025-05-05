package com.minipay.query.adapter.out.dynamodb;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MoneySumByAddressRepository {

    private final DynamoDbTable<MoneySumByAddress> table;

    public MoneySumByAddressRepository(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("money_sum_by_address", TableSchema.fromBean(MoneySumByAddress.class));
    }

    public void insertMoneySumByAddress(String address, BigDecimal changeAmount, UUID membershipId) {
        String currentDate = LocalDate.now().toString();

        insertRawChange(address, currentDate, changeAmount, membershipId);
        increaseDailySummary(address, currentDate, changeAmount);
        increaseTotalSummary(address, changeAmount);
    }

    private void insertRawChange(String address, String date, BigDecimal amount, UUID membershipId) {
        String pk = String.format("ADDR#%s#DATE#%s", address, date);
        String sk = membershipId.toString();

        MoneySumByAddress moneySumByAddress = new MoneySumByAddress(pk, sk, amount);
        table.putItem(moneySumByAddress);
    }

    private void increaseDailySummary(String address, String date, BigDecimal amount) {
        String pk = String.format("ADDR#%s#DATE#%s", address, date);
        String sk = "SUMMARY";

        updateOrInsertAmount(pk, sk, amount);
    }

    private void increaseTotalSummary(String address, BigDecimal amount) {
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
