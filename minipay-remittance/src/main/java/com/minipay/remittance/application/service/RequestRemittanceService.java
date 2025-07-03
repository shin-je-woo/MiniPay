package com.minipay.remittance.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.common.exception.BusinessException;
import com.minipay.remittance.application.port.in.RequestRemittanceCommand;
import com.minipay.remittance.application.port.in.RequestRemittanceUseCase;
import com.minipay.remittance.application.port.out.*;
import com.minipay.remittance.domain.Money;
import com.minipay.remittance.domain.Remittance;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RequestRemittanceService implements RequestRemittanceUseCase {

    private final RemittancePersistencePort remittancePersistencePort;
    private final MembershipServicePort membershipServicePort;
    private final MoneyServicePort moneyServicePort;
    private final BankingServicePort bankingServicePort;

    @Override
    public void requestRemittance(RequestRemittanceCommand command) {
        // 1. 송금 요청 생성
        Remittance remittance = createRemittance(command);

        // 2. 멤버십 상태 확인
        if (!membershipServicePort.isValidMembership(command.getSenderId())) {
            handleFailure(remittance, "송신자의 멤버쉽이 유효하지 않습니다.");
        }

        // 3. 잔액 확인 및 부족 시 충전 요청
        MoneyInfo senderMoneyInfo = moneyServicePort.getMoneyInfo(command.getSenderId());
        if (senderMoneyInfo.balance().compareTo(command.getAmount()) < 0) {
            BigDecimal rechargeAmount = command.getAmount().subtract(senderMoneyInfo.balance());
            if (!moneyServicePort.rechargeMoney(senderMoneyInfo.memberMoneyId(), rechargeAmount)) {
                handleFailure(remittance, "머니 충전에 실패했습니다.");
            }
        }

        // 4. 송금 처리 (내부 / 외부 송금)
        switch (remittance.getRemittanceType()) {
            case INTERNAL -> processInternalRemittance(senderMoneyInfo, remittance, command);
            case EXTERNAL -> processExternalRemittance(senderMoneyInfo, remittance, command);
        }

        // 5. 성공 처리 및 상태 업데이트
        remittance.success();
        remittancePersistencePort.updateRemittance(remittance);
    }

    private Remittance createRemittance(RequestRemittanceCommand command) {
        Remittance remittance = Remittance.newInstance(
                new Remittance.Sender(command.getSenderId()),
                new Remittance.Recipient(command.getRecipientId(), command.getDestBankName(), command.getDestBankAccountNumber()),
                new Money(command.getAmount())
        );
        remittancePersistencePort.createRemittance(remittance);
        return remittance;
    }

    private void processInternalRemittance(MoneyInfo senderMoneyInfo, Remittance remittance, RequestRemittanceCommand command) {
        if (!membershipServicePort.isValidMembership(remittance.getRecipient().membershipId())) {
            handleFailure(remittance, "수신자의 멤버쉽이 유효하지 않습니다.");
        }
        MoneyInfo recipientMoneyInfo = moneyServicePort.getMoneyInfo(command.getRecipientId());

        boolean isSuccessDecrease = moneyServicePort.decreaseMoney(senderMoneyInfo.memberMoneyId(), command.getAmount());
        boolean isSuccessIncrease = moneyServicePort.increaseMoney(recipientMoneyInfo.memberMoneyId(), command.getAmount());
        if (!isSuccessDecrease || !isSuccessIncrease) {
            handleFailure(remittance, "내부 고객 머니 송금에 실패했습니다.");
        }
    }

    private void processExternalRemittance(MoneyInfo senderMoneyInfo, Remittance remittance, RequestRemittanceCommand command) {
        boolean isSuccessWithdrawal = bankingServicePort.withdrawalFund(
                senderMoneyInfo.bankAccountId(),
                command.getDestBankName(),
                command.getDestBankAccountNumber(),
                command.getAmount()
        );
        if (!isSuccessWithdrawal) {
            handleFailure(remittance, "외부 은행 머니 송금에 실패했습니다.");
        }
    }

    private void handleFailure(Remittance remittance, String errorMessage) {
        remittance.fail();
        remittancePersistencePort.updateRemittance(remittance);
        throw new BusinessException(errorMessage);
    }
}