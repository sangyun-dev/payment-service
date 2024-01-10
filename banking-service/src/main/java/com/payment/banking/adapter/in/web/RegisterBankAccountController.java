package com.payment.banking.adapter.in.web;

import com.payment.banking.application.port.in.RegisterBankAccountCommand;
import com.payment.banking.application.port.in.RegisterBankAccountUseCase;
import com.payment.banking.domain.RegisteredBankAccount;
import com.payment.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase registerBankAccountUseCase;

    @PostMapping(path = "/banking/account/register")
    RegisteredBankAccount registeredBankAccount(@RequestBody RegisterBankAccountRequest request) {
        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .linkedStatusIsValid(request.isLinkedStatusIsValid())
                .build();
        RegisteredBankAccount registeredBankAccount = registerBankAccountUseCase.registerBankAccount(command);
        if (registeredBankAccount == null) {
            // Todo : Error handling
            return null;
        }

        return registeredBankAccount;
    }
}
