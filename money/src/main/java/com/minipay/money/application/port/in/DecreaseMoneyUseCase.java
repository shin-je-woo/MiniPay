package com.minipay.money.application.port.in;

import com.minipay.money.domain.MemberMoney;

public interface DecreaseMoneyUseCase {
    MemberMoney decreaseMoney(DecreaseMoneyCommand command);
    void decreaseMoneyAfterBanking(DecreaseMoneyAfterBankingCommand command);
}
