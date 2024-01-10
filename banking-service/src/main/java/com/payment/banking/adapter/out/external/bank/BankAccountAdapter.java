package com.payment.banking.adapter.out.external.bank;


import com.payment.banking.application.port.out.RequestBankAccountInfoPort;
import com.payment.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ExternalSystemAdapter
public class BankAccountAdapter implements RequestBankAccountInfoPort {

    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {

        return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
    }
}
