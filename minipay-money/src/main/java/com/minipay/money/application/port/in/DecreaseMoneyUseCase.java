package com.minipay.money.application.port.in;

import com.minipay.money.domain.model.MemberMoney;

public interface DecreaseMoneyUseCase {
    MemberMoney decreaseMoney(DecreaseMoneyCommand command);
    void decreaseMoneyAfterBanking(DecreaseMoneyAfterBankingCommand command);
    void decreaseMoneyByAxon(DecreaseMoneyCommand command);
}
