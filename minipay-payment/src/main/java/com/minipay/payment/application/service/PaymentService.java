package com.minipay.payment.application.service;

import com.minipay.common.annotation.UseCase;
import com.minipay.common.exception.BusinessException;
import com.minipay.payment.application.port.in.CreatePaymentCommand;
import com.minipay.payment.application.port.in.PaymentUseCase;
import com.minipay.payment.application.port.out.MembershipServicePort;
import com.minipay.payment.application.port.out.PaymentPersistencePort;
import com.minipay.payment.domain.Money;
import com.minipay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

    private final PaymentPersistencePort paymentPersistencePort;
    private final MembershipServicePort membershipServicePort;

    @Override
    public Payment createPayment(CreatePaymentCommand command) {
        if (!membershipServicePort.isValidMembership(command.getBuyerId())) {
            throw new BusinessException("Buyer 유효하지 않습니다.");
        }
        if (!membershipServicePort.isValidMembership(command.getSellerId())) {
            throw new BusinessException("Seller 유효하지 않습니다.");
        }
        Payment payment = Payment.newInstance(
                new Payment.MembershipId(command.getBuyerId()),
                new Payment.MembershipId(command.getSellerId()),
                new Money(command.getPrice()),
                new Payment.FeeRate(command.getFeeRate())
        );
        paymentPersistencePort.createPayment(payment);
        return payment;
    }
}
