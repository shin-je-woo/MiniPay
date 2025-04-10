package com.minipay.money.adapter.in.axon.saga;

import com.minipay.money.domain.event.RechargeMoneyRequestedEvent;
import com.minipay.saga.command.CheckBankAccountCommand;
import com.minipay.saga.command.OrderDepositFundCommand;
import com.minipay.saga.event.CheckBankAccountSucceededEvent;
import com.minipay.saga.event.OrderDepositFundSucceededEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
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

        UUID checkBankAccountId = UUID.randomUUID();
        SagaLifecycle.associateWith("checkBankAccountId", checkBankAccountId.toString());

        commandGateway.send(new CheckBankAccountCommand(
                event.bankAccountId(),
                checkBankAccountId,
                event.rechargeRequestId(),
                event.memberMoneyId(),
                event.moneyHistoryId(),
                event.membershipId(),
                event.amount()
        ));
    }

    @SagaEventHandler(associationProperty = "checkBankAccountId")
    public void handle(CheckBankAccountSucceededEvent event) {
        log.info("[Saga] 계좌 확인 성공 - ID : {}", event.checkBankAccountId());

        UUID orderDepositFundId = UUID.randomUUID();
        SagaLifecycle.associateWith("orderDepositFundId", orderDepositFundId.toString());

        commandGateway.send(new OrderDepositFundCommand(
                orderDepositFundId,
                event.bankAccountId(),
                event.checkBankAccountId(),
                event.memberMoneyId(),
                event.moneyHistoryId(),
                event.amount(),
                event.bankName(),
                event.accountNumber()
        ));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderDepositFundId")
    public void handle(OrderDepositFundSucceededEvent event) {
        log.info("[Saga] 입금 성공 - ID : {}", event.orderDepositFundId());
    }
}
