package com.minipay.banking.application.service;

import com.minipay.banking.application.port.in.RegisterBankAccountCommand;
import com.minipay.banking.application.port.in.RegisterBankAccountUseCase;
import com.minipay.banking.application.port.out.BankAccountInfo;
import com.minipay.banking.application.port.out.CreateBankAccountPort;
import com.minipay.banking.application.port.out.FetchBankAccountInfoPort;
import com.minipay.banking.domain.BankAccount;
import com.minipay.common.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterBankAccountService implements RegisterBankAccountUseCase {

    private final CreateBankAccountPort createBankAccountPort;
    private final FetchBankAccountInfoPort fetchBankAccountInfoPort;

    @Override
    public BankAccount registerBankAccount(RegisterBankAccountCommand command) {
        // 1. 외부 은행에서 계좌 정보 가져오기
        BankAccountInfo bankAccountInfo = fetchBankAccountInfoPort.fetchBankAccountInfo(
                command.getBankName(),
                command.getBankAccountNumber()
        ).orElseThrow(() -> new IllegalArgumentException("요청한 정보의 계좌가 존재하지 않습니다."));

        // 2. 유효한 계좌라면 등록한다.
        if (!bankAccountInfo.isValid()) {
            throw new IllegalArgumentException("연결된 계좌의 상태가 유효하지 않습니다");
        }
        BankAccount bankAccount = BankAccount.create(
                new BankAccount.OwnerId(command.getOwnerId()),
                bankAccountInfo.bankName(),
                bankAccountInfo.accountNumber(),
                true
        );
        return createBankAccountPort.createBankAccount(bankAccount);
    }
}
