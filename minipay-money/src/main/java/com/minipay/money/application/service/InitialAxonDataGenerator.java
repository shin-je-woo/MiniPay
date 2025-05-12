package com.minipay.money.application.service;

import com.minipay.common.exception.DataNotFoundException;
import com.minipay.money.application.port.in.*;
import com.minipay.money.application.port.out.BankAccountInfo;
import com.minipay.money.application.port.out.BankingServicePort;
import com.minipay.money.domain.model.MemberMoney;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitialAxonDataGenerator {

    @Value("${app.generate-initial-data:false}")
    private boolean generateInitialDataEnabled;
    private static final List<String> BANK_NAMES = List.of("신한은행", "우리은행", "하나은행");
    private static final int MIN_AMOUNT = 1_000;
    private static final int MAX_AMOUNT = 1_000_000;

    private final BankingServicePort bankingServicePort;
    private final GetMemberMoneyUseCase getMemberMoneyUseCase;
    private final RechargeMoneyUseCase rechargeMoneyUseCase;
    private final RegisterMemberMoneyUseCase registerMemberMoneyUseCase;
    private final ObjectProvider<InitialAxonDataGenerator> initialDataGeneratorProvider;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        if (generateInitialDataEnabled) {
            initialDataGeneratorProvider.getObject().generateInitialData();
        }
    }

    /**
     * Axon Projection이 수행하는 트랜잭션에서 insert한 데이터를 이 트랜잭션에서 읽기 위해, 격리 수준을 READ_COMMITTED로 설정.
     * because. REPEATABLE_READ 에서는 트랜잭션이 시작된 이후 다른 트랜잭션에서 insert한 데이터가 보이지 않기 때문.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void generateInitialData() {
        BANK_NAMES.forEach(bankName -> {
            List<BankAccountInfo> bankAccounts = bankingServicePort.getBankAccountsByBankName(bankName);
            bankAccounts.forEach(bankAccountInfo -> {
                RegisterMemberMoneyCommand registerMemberMoneyCommand = new RegisterMemberMoneyCommand(
                        bankAccountInfo.membershipId(),
                        bankAccountInfo.bankAccountId()
                );
                registerMemberMoneyUseCase.registerMemberMoneyByAxon(registerMemberMoneyCommand);
                MemberMoney memberMoney = pollingMemberMoney(bankAccountInfo.membershipId());
                RequestMoneyRechargeCommand requestMoneyRechargeCommand = new RequestMoneyRechargeCommand(
                        memberMoney.getMemberMoneyId().value(),
                        generateRandomAmount()
                );
                rechargeMoneyUseCase.requestMoneyRechargeByAxon(requestMoneyRechargeCommand);
            });
        });
    }

    private BigDecimal generateRandomAmount() {
        Random random = new Random();
        return BigDecimal.valueOf(
                random.nextInt((MAX_AMOUNT - MIN_AMOUNT) / 1000 + 1) * 1000 + MIN_AMOUNT
        );
    }

    private MemberMoney pollingMemberMoney(UUID membershipId) {
        int retry = 0;
        while (retry < 10) {
            try {
                Thread.sleep(100);
                return getMemberMoneyUseCase.getMemberMoneyByMembershipId(new MemberMoney.MembershipId(membershipId));
            } catch (DataNotFoundException | InterruptedException e) {
                log.info("pollingMemberMoney membershipId = {}, retry count = {}", membershipId, ++retry);
            }
        }
        throw new IllegalStateException("MemberMoney Projection이 아직 반영되지 않았습니다.");
    }
}
