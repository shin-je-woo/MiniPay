package com.minipay.banking.adapter.in.axon.saga;

import com.minipay.banking.application.port.in.DepositFundUseCase;
import com.minipay.banking.application.port.out.FirmBankingResult;
import com.minipay.saga.event.DepositFundFailedEvent;
import com.minipay.saga.event.DepositFundSucceededEvent;
import com.minipay.saga.command.DepositFundCommandFactory;
import com.minipay.saga.event.DepositFundCreatedEvent;
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
        CompletableFuture.supplyAsync(() -> depositFundUseCase.depositBySaga(event))
                .exceptionally(ex -> {
                    log.error("[Saga] 입금 트랜잭션 처리중 예외 발생", ex);
                    return new FirmBankingResult(false);
                })
                .thenApply(firmBankingResult -> firmBankingResult.isSucceeded() ?
                        commandGateway.send(DepositFundCommandFactory.successCommand(event)) :
                        commandGateway.send(DepositFundCommandFactory.failureCommand(event))
                );
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
