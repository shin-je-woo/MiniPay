package com.minipay.banking.application.service;

import com.minipay.banking.application.port.in.RegisterBankAccountCommand;
import com.minipay.banking.application.port.in.RegisterBankAccountUseCase;
import com.minipay.banking.application.port.out.BankAccountPersistencePort;
import com.minipay.banking.application.port.out.ExternalBankAccountInfo;
import com.minipay.banking.application.port.out.ExternalBankingPort;
import com.minipay.banking.application.port.out.MembershipServicePort;
import com.minipay.banking.domain.model.BankAccount;
import com.minipay.banking.domain.model.ExternalBankAccount;
import com.minipay.common.annotation.UseCase;
import com.minipay.common.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterBankAccountService implements RegisterBankAccountUseCase {

    private final BankAccountPersistencePort bankAccountPersistencePort;
    private final ExternalBankingPort externalBankingPort;
    private final MembershipServicePort membershipServicePort;

    @Override
    public BankAccount registerBankAccount(RegisterBankAccountCommand command) {
        // 1. 요청한 멤버쉽이 정상인지 확인
        if (!membershipServicePort.isValidMembership(command.getMembershipId())) {
            throw new BusinessException("멤버쉽이 유효하지 않습니다.");
        }

        // 2. 외부 은행에서 계좌 정보 가져오기
        ExternalBankAccountInfo externalBankAccountInfo = externalBankingPort.getBankAccountInfo(
                command.getBankName(),
                command.getBankAccountNumber()
        );

        // 3. 유효한 계좌라면 등록한다.
        if (!externalBankAccountInfo.isValid()) {
            throw new BusinessException("연결된 계좌의 상태가 유효하지 않습니다");
        }

        BankAccount bankAccount = BankAccount.newInstance(
                new BankAccount.MembershipId(command.getMembershipId()),
                new ExternalBankAccount(
                        new ExternalBankAccount.BankName(externalBankAccountInfo.bankName()),
                        new ExternalBankAccount.AccountNumber(externalBankAccountInfo.accountNumber())
                )
        );

        return bankAccountPersistencePort.createBankAccount(bankAccount);
    }
}
