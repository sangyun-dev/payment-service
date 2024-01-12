package com.payment.banking.adapter.out.persistence;

import com.payment.banking.domain.FirmBankingRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FirmbankingRequestMapper {

    public FirmBankingRequest mapToDomainEntity(FirmbankingRequestJpaEntity requestFirmbankingJpaEntity, String uuid) {

        return FirmBankingRequest.generateFirmbankingRequest(
                new FirmBankingRequest.FirmbankingRequestId(requestFirmbankingJpaEntity.getFirmbankingRequestId() + ""),
                new FirmBankingRequest.FromBankName(requestFirmbankingJpaEntity.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(requestFirmbankingJpaEntity.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(requestFirmbankingJpaEntity.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(requestFirmbankingJpaEntity.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(requestFirmbankingJpaEntity.getMoneyAmount()),
                new FirmBankingRequest.FirmbankingStatus(requestFirmbankingJpaEntity.getFirmbankingStatus()),
                uuid
        );
    }
}
