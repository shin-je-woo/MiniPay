package com.minipay.money.adapter.in.axon.saga;

import com.minipay.money.domain.event.RechargeMoneyRequestedEvent;
import com.minipay.saga.command.CheckLinkedBankAccountCommand;
import com.minipay.saga.event.BankAccountCheckFailedEvent;
import com.minipay.saga.event.BankAccountCheckSucceededEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Saga
@Component
public class MoneyRechargeSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "rechargeRequestId")
    public void handle(RechargeMoneyRequestedEvent event) {
        log.info("[Saga] 충전 요청 시작 - ID : {}", event.rechargeRequestId());

        UUID checkLinkedBankAccountId = UUID.randomUUID();
        SagaLifecycle.associateWith("checkLinkedBankAccountId", checkLinkedBankAccountId.toString());

        commandGateway.send(new CheckLinkedBankAccountCommand(
                event.bankAccountId(),
                checkLinkedBankAccountId,
                event.rechargeRequestId(),
                event.memberMoneyId(),
                event.moneyHistoryId(),
                event.membershipId(),
                event.amount()
        ));
    }

    @SagaEventHandler(associationProperty = "checkLinkedBankAccountId")
    public void handle(BankAccountCheckSucceededEvent event) {
        log.info("[Saga] 계좌 확인 성공 - ID : {}", event.checkLinkedBankAccountId());
    }

    @SagaEventHandler(associationProperty = "checkLinkedBankAccountId")
    public void handle(BankAccountCheckFailedEvent event) {
        log.info("[Saga] 계좌 확인 실패 - ID : {}", event.checkLinkedBankAccountId());
        SagaLifecycle.end();
    }
}
