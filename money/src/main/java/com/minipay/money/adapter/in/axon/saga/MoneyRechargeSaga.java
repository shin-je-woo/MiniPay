package com.minipay.money.adapter.in.axon.saga;

import com.minipay.money.domain.event.RechargeMoneyRequestedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    }
}
