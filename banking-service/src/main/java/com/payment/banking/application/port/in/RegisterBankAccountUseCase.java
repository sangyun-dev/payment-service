package com.payment.banking.application.port.in;

import com.payment.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountUseCase {

    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command);
}
