package com.minipay.money.application.service;

import com.minipay.common.exception.BusinessException;
import com.minipay.money.application.port.in.IncreaseMoneyCommand;
import com.minipay.money.application.port.in.IncreaseMoneyUseCase;
import com.minipay.money.application.port.out.CreateMoneyHistoryPort;
import com.minipay.money.application.port.out.GetMembershipPort;
import com.minipay.money.application.port.out.LoadMemberMoneyPort;
import com.minipay.money.application.port.out.UpdateMemberMoneyPort;
import com.minipay.money.domain.MemberMoney;
import com.minipay.money.domain.Money;
import com.minipay.money.domain.MoneyHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncreaseMoneyService implements IncreaseMoneyUseCase {

    private final LoadMemberMoneyPort loadMemberMoneyPort;
    private final UpdateMemberMoneyPort updateMemberMoneyPort;
    private final CreateMoneyHistoryPort createMoneyHistoryPort;
    private final GetMembershipPort getMembershipPort;

    @Override
    public MemberMoney requestMoneyIncrease(IncreaseMoneyCommand command) {
        MemberMoney memberMoney = loadMemberMoneyPort.loadMemberMoney(new MemberMoney.MemberMoneyId(command.getMemberMoneyId()));

        // 1. 고객 정보가 정상인지 확인 [멤버 서비스]
        if (!getMembershipPort.isValidMembership(memberMoney.getMembershipId().value())) {
            throw new BusinessException("멤버쉽이 유효하지 않습니다.");
        }

        // 2. 고객의 연동된 계좌가 있는지, 연동된 계좌의 잔액이 충분한지 확인 [뱅킹 서비스]

        // 3. 법인 계좌 상태가 정상인지 확인 [뱅킹 서비스]

        // 4. 펌뱅킹 수행 (고객의 연동 계좌 -> 법인 계좌) [뱅킹 서비스]
        MoneyHistory moneyHistory = memberMoney.requestIncreaseMoney(new Money(command.getAmount()));
        createMoneyHistoryPort.createMoneyHistory(moneyHistory);

        // 5. 펌뱅킹 결과에 따라 Money 상태 update 및 History 저장
//        MemberMoney increasedMemberMoney = memberMoney.increaseBalance(new Money(command.getAmount()));

        return memberMoney;
    }
}
