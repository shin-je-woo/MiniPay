package com.minipay.banking.adapter.in.axon.saga;

import com.minipay.banking.adapter.in.axon.commnad.FailDepositFundCommand;
import com.minipay.banking.adapter.in.axon.commnad.SucceedDepositFundCommand;
import com.minipay.banking.application.port.in.DepositFundUseCase;
import com.minipay.banking.application.port.out.FirmBankingResult;
import com.minipay.banking.domain.event.DepositFundCreatedEvent;
import com.minipay.banking.domain.event.DepositFundFailedEvent;
import com.minipay.banking.domain.event.DepositFundSucceededEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Saga
@Component
public class DepositFundTransactionSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient DepositFundUseCase depositFundUseCase;

    @StartSaga
    @SagaEventHandler(associationProperty = "fundTransactionId")
    public void handle(DepositFundCreatedEvent event) {
        log.info("[Saga] 입금 트랜잭션 시작 - ID : {}", event.fundTransactionId());
        CompletableFuture.supplyAsync(() -> depositFundUseCase.processDepositByAxon(event))
                .exceptionally(ex -> {
                    log.error("[Saga] 입금 트랜잭션 처리중 예외 발생", ex);
                    return new FirmBankingResult(false);
                })
                .thenAccept(firmBankingResult -> {
                    if (firmBankingResult.isSucceeded()) {
                        commandGateway.send(new SucceedDepositFundCommand(event.fundTransactionId()));
                    } else {
                        commandGateway.send(new FailDepositFundCommand(event.fundTransactionId()));
                    }
                });
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "fundTransactionId")
    public void on(DepositFundSucceededEvent event) {
        log.info("[Saga] 입금 트랜잭션 성공, Saga 종료 - ID : {})", event.fundTransactionId());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "fundTransactionId")
    public void on(DepositFundFailedEvent event) {
        log.info("[Saga] 입금 트랜잭션 실패, Saga 종료 - ID: {})", event.fundTransactionId());
    }
}
