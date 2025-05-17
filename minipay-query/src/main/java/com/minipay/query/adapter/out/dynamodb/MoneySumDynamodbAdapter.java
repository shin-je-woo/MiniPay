package com.minipay.query.adapter.out.dynamodb;

import com.minipay.query.adapter.in.axon.dto.QueryDailyMoneySumByAddress;
import com.minipay.query.adapter.in.axon.dto.QueryMoneySumByAddress;
import com.minipay.query.adapter.in.axon.dto.QueryTopMoneySumByMembership;
import com.minipay.query.application.port.out.GetMoneySumPort;
import com.minipay.query.application.port.out.InsertMoneySumPort;
import com.minipay.query.application.port.out.MoneySumByMembershipsResult;
import com.minipay.query.domain.MoneySumByMembership;
import com.minipay.query.domain.MoneySumByRegion;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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

        MoneySumByAddress moneySumByAddress = new MoneySumByAddress(pk, sk, address, changeAmount);
        table.putItem(moneySumByAddress);
    }

    @Override
    public void upsertDailySummary(String address, BigDecimal changeAmount, LocalDate changeDate) {
        String pk = String.format("ADDR#%s#DATE#%s", address, changeDate);
        String sk = "SUMMARY";

        upsertAmount(pk, sk, changeAmount, address);
    }

    @Override
    public void upsertTotalSummary(String address, BigDecimal amount) {
        String pk = String.format("ADDR#%s", address);
        String sk = "TOTAL";

        upsertAmount(pk, sk, amount, address);
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

    @Override
    public List<MoneySumByMembership> getTopMoneySumByMembership(String address, int fetchSize) {
        DynamoDbIndex<MoneySumByAddress> balanceIndex = table.index("address-balance-index");
        Map<String, BigDecimal> aggregatedSums = new HashMap<>();

        QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                .queryConditional(
                        QueryConditional.keyEqualTo(Key.builder()
                                .partitionValue(address)
                                .build())
                )
                .filterExpression(Expression.builder()
                        .expression("sk <> :summary AND sk <> :total")
                        .putExpressionValue(":summary", AttributeValue.fromS("SUMMARY"))
                        .putExpressionValue(":total", AttributeValue.fromS("TOTAL"))
                        .build())
                .scanIndexForward(false)
                .build();

        // 페이지 단위로 처리하면서 합산
        balanceIndex.query(queryRequest)
                .stream()
                .map(Page::items)
                .flatMap(List::stream)
                .forEach(item -> {
                    String membershipId = item.getSk();
                    aggregatedSums.merge(membershipId, item.getBalance(), BigDecimal::add);
                });

        // 합산된 결과를 정렬하고 상위 N개 반환
        return aggregatedSums.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(fetchSize)
                .map(entry -> MoneySumByMembership.newInstance(
                        new MoneySumByMembership.MembershipId(UUID.fromString(entry.getKey())),
                        new MoneySumByMembership.RegionName(address),
                        new MoneySumByMembership.MoneySum(entry.getValue())
                ))
                .toList();
    }

    @QueryHandler
    public MoneySumByRegion query(QueryMoneySumByAddress query) {
        return getMoneySumByAddress(query.address());
    }

    @QueryHandler
    public MoneySumByRegion query(QueryDailyMoneySumByAddress query) {
        return getDailyMoneySumByAddress(query.address(), query.date());
    }

    @QueryHandler
    public MoneySumByMembershipsResult query(QueryTopMoneySumByMembership query) {
        List<MoneySumByMembership> moneySumByMemberships = getTopMoneySumByMembership(query.address(), query.fetchSize());
        return new MoneySumByMembershipsResult(moneySumByMemberships);
    }

    private void upsertAmount(String pk, String sk, BigDecimal amount, String address) {
        Key key = Key.builder().partitionValue(pk).sortValue(sk).build();

        Optional.ofNullable(table.getItem(r -> r.key(key)))
                .ifPresentOrElse(
                        existing -> {
                            BigDecimal newBalance = existing.getBalance().add(amount);
                            existing.setBalance(newBalance);
                            table.updateItem(existing);
                        },
                        () -> {
                            MoneySumByAddress newItem = new MoneySumByAddress(pk, sk, address, amount);
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
