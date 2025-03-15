package com.minipay.banking.adapter.in.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minipay.banking.application.port.in.DepositMinipayMoneyCommand;
import com.minipay.banking.application.port.in.DepositMinipayFundUseCase;
import com.minipay.common.constants.Topic;
import com.minipay.common.event.DomainEvent;
import com.minipay.common.event.EventType;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMoneyEventConsumer {

    private final ObjectMapper objectMapper;
    private final DepositMinipayFundUseCase depositMinipayFundUseCase;

    /**
     * 고객의 머니 충전 요청이 발생하면 고객의 연동계좌에서 미니페이 법인계좌로 입금한다.
     */
    @KafkaListener(
            topics = {Topic.MEMBER_MONEY_EVENTS},
            groupId = "banking.minipay-fund.deposit",
            concurrency = "3"
    )
    public void depositMinipayFund(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) throws JsonProcessingException {
        String message = record.value();
        DomainEvent domainEvent = objectMapper.readValue(message, DomainEvent.class);
        MemberMoneyEventPayload payload = objectMapper.convertValue(domainEvent.getPayload(), MemberMoneyEventPayload.class);

        if (domainEvent.getEventType() == EventType.MEMBER_MONEY_RECHARGE_REQUESTED) {
            handleRechargeMoneyRequestedEvent(payload);
        }

        acknowledgment.acknowledge();
    }

    private void handleRechargeMoneyRequestedEvent(MemberMoneyEventPayload payload) {
        DepositMinipayMoneyCommand command = DepositMinipayMoneyCommand.builder()
                .bankAccountId(payload.bankAccountId())
                .moneyHistoryId(payload.moneyHistoryId())
                .amount(payload.amount())
                .build();
        depositMinipayFundUseCase.deposit(command);
    }
}
