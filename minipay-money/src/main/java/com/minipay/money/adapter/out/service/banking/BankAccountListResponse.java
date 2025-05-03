package com.minipay.money.adapter.out.service.banking;

import java.util.List;

public record BankAccountListResponse(
        List<BankAccountResponse> bankAccounts
) {
}
