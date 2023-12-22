package com.payment.membership.application.service;

import com.payment.membership.adapter.out.persistence.MembershipJpaEntity;
import com.payment.membership.adapter.out.persistence.MembershipMapper;
import com.payment.membership.application.port.in.FindMembershipCommand;
import com.payment.membership.application.port.in.FindMembershipUseCase;
import com.payment.membership.application.port.out.FindMembershipPort;
import com.payment.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort findMembershipPort;

    private final MembershipMapper membershipMapper;
    
    @Override
    public Membership findMembership(FindMembershipCommand command) {


        MembershipJpaEntity jpaEntity = findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId()));
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
