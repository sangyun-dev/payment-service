package com.payment.banking.adapter.out.persistence;

import com.payment.banking.application.port.out.RegisterBankAccountPort;
import com.payment.banking.application.port.out.RequestFirmbankingPort;
import com.payment.banking.domain.FirmBankingRequest;
import com.payment.banking.domain.RegisteredBankAccount;
import com.payment.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmbankingRequestPersistenceAdaptor implements RequestFirmbankingPort {


    private final SpringDataFirmbankingRequestRepository springDataFirmbankingRequestRepository;

    @Override
    public FirmbankingRequestJpaEntity createFirmbankingRequest(FirmBankingRequest.FromBankName fromBankName, FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber, FirmBankingRequest.ToBankName toBankName, FirmBankingRequest.ToBankAccountNumber toBankAccountNumber, FirmBankingRequest.MoneyAmount moneyAmount, FirmBankingRequest.FirmbankingStatus firmbankingStatus) {

        return springDataFirmbankingRequestRepository.save(
                new FirmbankingRequestJpaEntity(
                        fromBankName.getFromBankName(),
                        fromBankAccountNumber.getFromBankAccountNumber(),
                        toBankName.getToBankName(),
                        toBankAccountNumber.getToBankAccountNumber(),
                        moneyAmount.getMoneyAmount(),
                        firmbankingStatus.getFirmbankingStatus()
                )
        );
    }

    @Override
    public FirmbankingRequestJpaEntity modifyFirmbankingRequest(FirmbankingRequestJpaEntity entity) {

        return springDataFirmbankingRequestRepository.save(entity);
    }
}
