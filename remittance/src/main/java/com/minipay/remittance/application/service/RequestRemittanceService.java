package com.minipay.remittance.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.common.exception.BusinessException;
import com.minipay.remittance.application.port.in.RequestRemittanceCommand;
import com.minipay.remittance.application.port.in.RequestRemittanceUseCase;
import com.minipay.remittance.application.port.out.*;
import com.minipay.remittance.domain.Money;
import com.minipay.remittance.domain.Remittance;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RequestRemittanceService implements RequestRemittanceUseCase {

    private final RemittancePersistencePort remittancePersistencePort;
    private final MembershipServicePort membershipServicePort;
    private final MoneyServicePort moneyServicePort;
    private final BankingServicePort bankingServicePort;
    private final ObjectProvider<RequestRemittanceService> requestRemittanceServiceProvider;

    @Override
    public void requestRemittance(RequestRemittanceCommand command) {
        // 1. 송금 요청 생성
        Remittance remittance = createRemittance(command);

        // 2. 멤버십 상태 확인
        if (!membershipServicePort.isValidMembership(command.getSenderId())) {
            handleFailure(remittance, "멤버쉽이 유효하지 않습니다.");
        }

        // 3. 잔액 확인 및 부족 시 충전 요청
        MoneyInfo moneyInfo = moneyServicePort.getMoneyInfo(command.getSenderId());
        if (moneyInfo.balance().compareTo(command.getAmount()) < 0) {
            BigDecimal increaseAmount = computeIncreaseAmount(command.getAmount(), moneyInfo.balance());
            if (!moneyServicePort.increaseMoney(command.getSenderId(), increaseAmount)) {
                handleFailure(remittance, "머니 충전에 실패했습니다.");
            }
        }

        // 4. 송금 처리 (내부 / 외부 송금)
        switch (remittance.getRemittanceType()) {
            case INTERNAL -> processInternalRemittance(command, remittance);
            case EXTERNAL -> processExternalRemittance(command, remittance);
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
        requestRemittanceServiceProvider.getObject().saveRemittance(remittance);
        return remittance;
    }

    private void processInternalRemittance(RequestRemittanceCommand command, Remittance remittance) {
        boolean isSuccessDecrease = moneyServicePort.decreaseMoney(command.getSenderId(), command.getAmount());
        boolean isSuccessIncrease = moneyServicePort.increaseMoney(command.getRecipientId(), command.getAmount());
        if (!isSuccessDecrease || !isSuccessIncrease) {
            handleFailure(remittance, "내부 고객 머니 송금에 실패했습니다.");
        }
    }

    private void processExternalRemittance(RequestRemittanceCommand command, Remittance remittance) {
        boolean isSuccessWithdrawal = bankingServicePort.withdrawalMinipayFund(
                command.getDestBankName(),
                command.getDestBankAccountNumber(),
                command.getAmount()
        );
        if (!isSuccessWithdrawal) {
            handleFailure(remittance, "외부 은행 머니 송금에 실패했습니다.");
        }
    }

    private BigDecimal computeIncreaseAmount(BigDecimal requested, BigDecimal current) {
        return requested.subtract(current)
                .divide(BigDecimal.valueOf(10000), RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(10000));
    }

    private void handleFailure(Remittance remittance, String errorMessage) {
        remittance.fail();
        requestRemittanceServiceProvider.getObject().saveRemittance(remittance);
        throw new BusinessException(errorMessage);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void saveRemittance(Remittance remittance) {
        switch (remittance.getRemittanceStatus()) {
            case REQUESTED -> remittancePersistencePort.createRemittance(remittance);
            case FAILED -> remittancePersistencePort.updateRemittance(remittance);
        }
    }
}