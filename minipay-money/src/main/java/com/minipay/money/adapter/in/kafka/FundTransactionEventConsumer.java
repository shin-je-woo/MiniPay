package com.minipay.money.adapter.in.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minipay.common.constants.Topic;
import com.minipay.common.event.DomainEvent;
import com.minipay.common.event.EventType;
import com.minipay.money.application.port.in.DecreaseMoneyAfterBankingCommand;
import com.minipay.money.application.port.in.DecreaseMoneyUseCase;
import com.minipay.money.application.port.in.RechargeMoneyCommand;
import com.minipay.money.application.port.in.RechargeMoneyUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FundTransactionEventConsumer {

    private final ObjectMapper objectMapper;
    private final RechargeMoneyUseCase rechargeMoneyUseCase;
    private final DecreaseMoneyUseCase decreaseMoneyUseCase;

    @KafkaListener(
            topics = {Topic.FUND_TRANSACTION_EVENTS},
            groupId = "money.member-money.recharge",
            concurrency = "3"
    )
    public void rechargeMemberMoney(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) throws JsonProcessingException {
        String message = record.value();
        DomainEvent domainEvent = objectMapper.readValue(message, DomainEvent.class);
        FundTransactionEventPayload payload = objectMapper.convertValue(domainEvent.getPayload(), FundTransactionEventPayload.class);

        if (domainEvent.getEventType() == EventType.MINIPAY_FUND_DEPOSITED) {
            handleDepositEvent(payload);
        }

        acknowledgment.acknowledge();
    }

    @KafkaListener(
            topics = {Topic.FUND_TRANSACTION_EVENTS},
            groupId = "money.member-money.decrease",
            concurrency = "3"
    )
    public void decreaseMemberMoney(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) throws JsonProcessingException {
        String message = record.value();
        DomainEvent domainEvent = objectMapper.readValue(message, DomainEvent.class);
        FundTransactionEventPayload payload = objectMapper.convertValue(domainEvent.getPayload(), FundTransactionEventPayload.class);

        if (domainEvent.getEventType() == EventType.MINIPAY_FUND_WITHDRAWN) {
            handleWithdrawalEvent(payload);
        }

        acknowledgment.acknowledge();
    }

    private void handleDepositEvent(FundTransactionEventPayload payload) {
        RechargeMoneyCommand command = RechargeMoneyCommand.builder()
                .moneyHistoryId(payload.moneyHistoryId())
                .build();
        rechargeMoneyUseCase.rechargeMoney(command);
    }

    private void handleWithdrawalEvent(FundTransactionEventPayload payload) {
        DecreaseMoneyAfterBankingCommand command = DecreaseMoneyAfterBankingCommand.builder()
                .bankAccountId(payload.bankAccountId())
                .amount(payload.amount())
                .build();
        decreaseMoneyUseCase.decreaseMoneyAfterBanking(command);
    }
}
