package com.minipay.banking.application.service;

import com.minipay.banking.application.port.in.RegisterBankAccountCommand;
import com.minipay.banking.application.port.in.RegisterBankAccountUseCase;
import com.minipay.banking.application.port.out.CreateBankAccountPort;
import com.minipay.banking.application.port.out.ExternalBankAccountInfo;
import com.minipay.banking.application.port.out.GetBankAccountInfoPort;
import com.minipay.banking.application.port.out.GetMembershipPort;
import com.minipay.banking.domain.BankAccount;
import com.minipay.common.annotation.UseCase;
import com.minipay.common.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterBankAccountService implements RegisterBankAccountUseCase {

    private final CreateBankAccountPort createBankAccountPort;
    private final GetBankAccountInfoPort getBankAccountInfoPort;
    private final GetMembershipPort getMembershipPort;

    @Override
    public BankAccount registerBankAccount(RegisterBankAccountCommand command) {
        // 1. 요청한 멤버쉽이 정상인지 확인
        if (!getMembershipPort.isValidMembership(command.getOwnerId())) {
            throw new BusinessException("멤버쉽이 유효하지 않습니다.");
        }

        // 2. 외부 은행에서 계좌 정보 가져오기
        ExternalBankAccountInfo externalBankAccountInfo = getBankAccountInfoPort.getBankAccountInfo(
                command.getBankName(),
                command.getBankAccountNumber()
        );

        // 3. 유효한 계좌라면 등록한다.
        if (!externalBankAccountInfo.isValid()) {
            throw new BusinessException("연결된 계좌의 상태가 유효하지 않습니다");
        }

        BankAccount bankAccount = BankAccount.create(
                new BankAccount.OwnerId(command.getOwnerId()),
                new BankAccount.LinkedBankAccount(
                        externalBankAccountInfo.bankName(),
                        externalBankAccountInfo.accountNumber(),
                        true
                )
        );
        return createBankAccountPort.createBankAccount(bankAccount);
    }
}
