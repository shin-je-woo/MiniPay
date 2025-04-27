package com.minipay.banking.application.service;

import com.minipay.banking.application.port.in.RegisterBankAccountCommand;
import com.minipay.banking.application.port.in.RegisterBankAccountUseCase;
import com.minipay.banking.application.port.out.MembershipServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InitialAxonDataGenerator {

    @Value("${app.generate-initial-data:false}")
    private boolean generateInitialDataEnabled;
    private static final List<String> ADDRESSES = List.of("강남구", "서초구", "광진구");
    private static final List<String> BANK_NAMES = List.of("신한은행", "우리은행", "하나은행");

    private final RegisterBankAccountUseCase registerBankAccountUseCase;
    private final MembershipServicePort membershipServicePort;
    private final ObjectProvider<InitialAxonDataGenerator> initialDataGeneratorProvider;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        if (generateInitialDataEnabled) {
            initialDataGeneratorProvider.getObject().generateInitialData();
        }
    }

    @Transactional
    public void generateInitialData() {
        ADDRESSES.forEach(address -> {
            List<UUID> membershipIds = membershipServicePort.getMembershipIdsByAddress(address);
            membershipIds.forEach(membershipId -> {
                RegisterBankAccountCommand registerBankAccountCommand = new RegisterBankAccountCommand(
                        membershipId,
                        BANK_NAMES.get(new Random().nextInt(BANK_NAMES.size())),
                        generateRandomAccountNumber()
                );
                registerBankAccountUseCase.registerBankAccountByAxon(registerBankAccountCommand);
            });
        });
    }

    private String generateRandomAccountNumber() {
        Random random = new Random();
        int length = 12 + random.nextInt(4);
        return random.ints(0, 10)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .insert(0, 1 + random.nextInt(9))
                .toString();
    }
}
