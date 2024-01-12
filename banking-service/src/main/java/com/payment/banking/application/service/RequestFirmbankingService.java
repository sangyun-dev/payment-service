package com.payment.banking.application.service;

import com.payment.banking.adapter.in.web.RequestFirmbankingRequest;
import com.payment.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.payment.banking.adapter.out.external.bank.FirmbankingResult;
import com.payment.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.payment.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.payment.banking.application.port.in.RequestFirmbankingCommand;
import com.payment.banking.application.port.in.RequestFirmbankingUseCase;
import com.payment.banking.application.port.out.RequestExternalFirmbankingPort;
import com.payment.banking.application.port.out.RequestFirmbankingPort;
import com.payment.banking.domain.FirmBankingRequest;
import com.payment.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase {

    private final RequestFirmbankingPort requestFirmbankingPort;

    private final FirmbankingRequestMapper firmbankingRequestMapper;

    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;

    @Override
    public FirmBankingRequest requestFirmBanking(RequestFirmbankingCommand command) {

        // 1. 요청에 대해 정보를 먼저 write "요청" 상태로
        FirmbankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmBankingRequest.FromBankName(command.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(command.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmBankingRequest.FirmbankingStatus(0)
        );

        // 2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult firmbankingResult = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount()
        ));

        String randomUUID = UUID.randomUUID().toString();
        requestedEntity.setUuid(randomUUID);

        // 3. 결과에 따라 1번에서 작성했던 FirmbankingRequest 정보를 update
        if (firmbankingResult.getResultCode() == 0) {
            requestedEntity.setFirmbankingStatus(1);
        } else {
            requestedEntity.setFirmbankingStatus(2);
        }


        return firmbankingRequestMapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), randomUUID);
    }
}
