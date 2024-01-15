package com.payment.banking.application.service;

import com.payment.banking.adapter.out.external.bank.BankAccount;
import com.payment.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.payment.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.payment.banking.application.port.in.RegisterBankAccountCommand;
import com.payment.banking.application.port.in.RegisterBankAccountUseCase;
import com.payment.banking.application.port.out.GetMembershipPort;
import com.payment.banking.application.port.out.MembershipStatus;
import com.payment.banking.application.port.out.RegisterBankAccountPort;
import com.payment.banking.application.port.out.RequestBankAccountInfoPort;
import com.payment.banking.domain.RegisteredBankAccount;
import com.payment.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase {

    private final RegisterBankAccountPort registerBankAccountPort;

    private final RegisteredBankAccountMapper registeredBankAccountMapper;

    private final RequestBankAccountInfoPort requestBankAccountInfoPort;

    private final GetMembershipPort getMembershipPort;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        // 은행 계좌를 등록해야하는 서비스 (비즈니스 로직)

        // (멤버 서비스 확인) 멤버 인증
        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getMembershipId());
        if (!membershipStatus.isValid()) {
            return null;
        }

        // 1. 외부 실제 등록이 가능한 계좌(정상)인지 확인한다.
        // Port -> Adapter -> External System

        //실제 외부의 은행계좌 정보를 Get
        BankAccount accountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(
                command.getBankName(),
                command.getBankAccountNumber()
        ));
        boolean accountIsValid = accountInfo.isValid();

        // 2. 등록 가능한 계좌라면, 등록한다, 성공시 등록한 정보를 리턴
        // 2-1. 등록 가능하지 않은 계좌라면, 에러를 리턴
        if (!accountIsValid) {
            return null;
        }

        return registeredBankAccountMapper.mapToDomainEntity(registerBankAccountPort.createRegisteredBankAccount(
                new RegisteredBankAccount.MembershipId(command.getMembershipId()),
                new RegisteredBankAccount.BankName(command.getBankName()),
                new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                new RegisteredBankAccount.LinkedStatusIsValid(command.isLinkedStatusIsValid())
        ));
    }
}
