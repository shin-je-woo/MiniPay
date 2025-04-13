package com.minipay.banking.adapter.in.axon.saga;

import com.minipay.banking.adapter.in.axon.commnad.FailWithdrawalFundCommand;
import com.minipay.banking.adapter.in.axon.commnad.SucceedWithdrawalFundCommand;
import com.minipay.banking.application.port.in.WithdrawalFundUseCase;
import com.minipay.banking.application.port.out.FirmBankingResult;
import com.minipay.banking.domain.event.DepositFundFailedEvent;
import com.minipay.banking.domain.event.DepositFundSucceededEvent;
import com.minipay.banking.domain.event.WithdrawalFundCreatedEvent;
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
public class WithdrawalFundTransactionSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient WithdrawalFundUseCase withdrawalFundUseCase;

    @StartSaga
    @SagaEventHandler(associationProperty = "fundTransactionId")
    public void handle(WithdrawalFundCreatedEvent event) {
        log.info("[Saga] 출금 트랜잭션 시작 - ID : {}", event.fundTransactionId());
        CompletableFuture.supplyAsync(() -> withdrawalFundUseCase.processWithdrawalByAxon(event))
                .exceptionally(ex -> {
                    log.error("[Saga] 출금 트랜잭션 처리중 예외 발생", ex);
                    return new FirmBankingResult(false);
                })
                .thenAccept(firmBankingResult -> {
                    if (firmBankingResult.isSucceeded()) {
                        commandGateway.send(new SucceedWithdrawalFundCommand(event.fundTransactionId()));
                    } else {
                        commandGateway.send(new FailWithdrawalFundCommand(event.fundTransactionId()));
                    }
                });
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "fundTransactionId")
    public void on(DepositFundSucceededEvent event) {
        log.info("[Saga] 출금 트랜잭션 성공, Saga 종료 - ID : {})", event.fundTransactionId());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "fundTransactionId")
    public void on(DepositFundFailedEvent event) {
        log.info("[Saga] 출금 트랜잭션 실패, Saga 종료 - ID: {})", event.fundTransactionId());
    }
}
