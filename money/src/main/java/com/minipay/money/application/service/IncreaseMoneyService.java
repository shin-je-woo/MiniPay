package com.minipay.money.application.service;

import com.minipay.money.application.port.in.IncreaseMoneyCommand;
import com.minipay.money.application.port.in.IncreaseMoneyUseCase;
import com.minipay.money.application.port.out.CreateMoneyHistoryPort;
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

    @Override
    public MemberMoney increaseMoney(IncreaseMoneyCommand command) {
        // 1. 고객 정보가 정상인지 확인 [멤버 서비스]

        // 2. 고객의 연동된 계좌가 있는지, 연동된 계좌의 잔액이 충분한지 확인 [뱅킹 서비스]

        // 3. 법인 계좌 상태가 정상인지 확인 [뱅킹 서비스]

        // 4. 펌뱅킹 수행 (고객의 연동 계좌 -> 법인 계좌) [뱅킹 서비스]

        // 5. 펌뱅킹 결과에 따라 Money 상태 update 및 History 저장
        MemberMoney memberMoney = loadMemberMoneyPort.loadMemberMoney(new MemberMoney.MemberMoneyId(command.getMemberMoneyId()));
        MemberMoney increasedMemberMoney = memberMoney.increaseBalance(new Money(command.getAmount()));

        MoneyHistory moneyHistory = MoneyHistory.newInstance(
                increasedMemberMoney.getMemberMoneyId(),
                MoneyHistory.ChangeType.INCREASE,
                new Money(command.getAmount()),
                increasedMemberMoney.getBalance()
        );
        createMoneyHistoryPort.createMoneyHistory(moneyHistory);

        return updateMemberMoneyPort.updateMemberMoney(increasedMemberMoney);
    }
}
