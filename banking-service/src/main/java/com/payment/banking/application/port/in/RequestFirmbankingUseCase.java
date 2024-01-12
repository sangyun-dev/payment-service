package com.payment.banking.application.port.in;

import com.payment.banking.domain.FirmBankingRequest;

public interface RequestFirmbankingUseCase {

    FirmBankingRequest requestFirmBanking(RequestFirmbankingCommand request);
}
