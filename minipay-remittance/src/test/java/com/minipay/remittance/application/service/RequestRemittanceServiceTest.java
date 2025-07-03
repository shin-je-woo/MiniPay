package com.minipay.remittance.application.service;

import com.minipay.common.exception.BusinessException;
import com.minipay.remittance.application.port.in.RequestRemittanceCommand;
import com.minipay.remittance.application.port.out.*;
import com.minipay.remittance.domain.Remittance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestRemittanceServiceTest {

    @InjectMocks
    private RequestRemittanceService requestRemittanceService;

    @Mock
    private MembershipServicePort membershipServicePort;

    @Mock
    private MoneyServicePort moneyServicePort;

    @Mock
    private BankingServicePort bankingServicePort;

    @Mock
    private RemittancePersistencePort remittancePersistencePort;

    @Test
    void 송금_정상동작_조건만족() {
        UUID senderId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);

        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .senderId(senderId)
                .recipientId(recipientId)
                .amount(amount)
                .destBankName("SomeBank")
                .destBankAccountNumber("12345678")
                .build();

        when(membershipServicePort.isValidMembership(any(UUID.class))).thenReturn(true);
        when(moneyServicePort.getMoneyInfo(senderId)).thenReturn(
                new MoneyInfo(senderId, UUID.randomUUID(), BigDecimal.valueOf(300)));
        when(moneyServicePort.getMoneyInfo(recipientId)).thenReturn(
                new MoneyInfo(recipientId, UUID.randomUUID(), BigDecimal.valueOf(100)));
        when(moneyServicePort.decreaseMoney(any(), any())).thenReturn(true);
        when(moneyServicePort.increaseMoney(any(), any())).thenReturn(true);

        requestRemittanceService.requestRemittance(command);

        verify(moneyServicePort, times(1)).increaseMoney(any(), eq(amount));
        verify(moneyServicePort, times(1)).decreaseMoney(any(), eq(amount));

        verify(remittancePersistencePort, times(1)).createRemittance(any(Remittance.class));
        verify(remittancePersistencePort, times(1)).updateRemittance(any(Remittance.class));
    }

    @Test
    void 송금_실패_송신자멤버십유효하지않음() {
        UUID senderId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);

        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .senderId(senderId)
                .recipientId(recipientId)
                .amount(amount)
                .destBankName("SomeBank")
                .destBankAccountNumber("12345678")
                .build();

        when(membershipServicePort.isValidMembership(senderId)).thenReturn(false);

        assertThatThrownBy(() -> requestRemittanceService.requestRemittance(command))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("송신자의 멤버쉽이 유효하지 않습니다.");

        verify(remittancePersistencePort, times(1)).createRemittance(any(Remittance.class));
        verify(remittancePersistencePort, times(1)).updateRemittance(any(Remittance.class));
    }

    @Test
    void 송금_실패_잔액부족충전실패() {
        UUID senderId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(500);

        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .senderId(senderId)
                .recipientId(recipientId)
                .amount(amount)
                .destBankName("SomeBank")
                .destBankAccountNumber("12345678")
                .build();

        when(membershipServicePort.isValidMembership(any(UUID.class))).thenReturn(true);
        when(moneyServicePort.getMoneyInfo(senderId)).thenReturn(
                new MoneyInfo(senderId, UUID.randomUUID(), BigDecimal.valueOf(400)));
        when(moneyServicePort.rechargeMoney(any(), any())).thenReturn(false);

        assertThatThrownBy(() -> requestRemittanceService.requestRemittance(command))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("머니 충전에 실패했습니다.");

        verify(remittancePersistencePort, times(1)).createRemittance(any(Remittance.class));
        verify(remittancePersistencePort, times(1)).updateRemittance(any(Remittance.class));
    }

    @Test
    void 송금_실패_내부수신자멤버십유효하지않음() {
        UUID senderId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);

        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .senderId(senderId)
                .recipientId(recipientId)
                .amount(amount)
                .destBankName("SomeBank")
                .destBankAccountNumber("12345678")
                .build();

        when(membershipServicePort.isValidMembership(senderId)).thenReturn(true);
        when(moneyServicePort.getMoneyInfo(senderId)).thenReturn(
                new MoneyInfo(senderId, UUID.randomUUID(), BigDecimal.valueOf(500)));
        when(membershipServicePort.isValidMembership(recipientId)).thenReturn(false);

        assertThatThrownBy(() -> requestRemittanceService.requestRemittance(command))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("수신자의 멤버쉽이 유효하지 않습니다.");

        verify(remittancePersistencePort, times(1)).createRemittance(any(Remittance.class));
        verify(remittancePersistencePort, times(1)).updateRemittance(any(Remittance.class));
    }

    @Test
    void 송금_실패_외부은행출금실패() {
        UUID senderId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100);

        RequestRemittanceCommand command = RequestRemittanceCommand.builder()
                .senderId(senderId)
                .recipientId(null)
                .amount(amount)
                .destBankName("SomeBank")
                .destBankAccountNumber("12345678")
                .build();

        when(membershipServicePort.isValidMembership(senderId)).thenReturn(true);
        when(moneyServicePort.getMoneyInfo(senderId)).thenReturn(
                new MoneyInfo(senderId, UUID.randomUUID(), BigDecimal.valueOf(500)));
        when(bankingServicePort.withdrawalFund(any(), any(), any(), any())).thenReturn(false);

        assertThatThrownBy(() -> requestRemittanceService.requestRemittance(command))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("외부 은행 머니 송금에 실패했습니다.");

        verify(remittancePersistencePort, times(1)).createRemittance(any(Remittance.class));
        verify(remittancePersistencePort, times(1)).updateRemittance(any(Remittance.class));
    }
}