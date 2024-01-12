package com.payment.banking.adapter.out.external.bank;


import com.payment.banking.application.port.out.RequestBankAccountInfoPort;
import com.payment.banking.application.port.out.RequestExternalFirmbankingPort;
import com.payment.banking.domain.FirmBankingRequest;
import com.payment.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ExternalSystemAdapter
public class BankAccountAdapter implements RequestBankAccountInfoPort, RequestExternalFirmbankingPort {

    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {

        return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
    }

    @Override
    public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request) {

        // 외부 은행에 http 통신 요청 후 펌뱅킹 결과를 토대로 상태를 변경

        // 항상 성공이라고 가정
        return new FirmbankingResult(0);
    }
}
