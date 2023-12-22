package com.payment.membership.adapter.out.persistence;

import com.payment.membership.application.port.out.FindMembershipPort;
import com.payment.membership.application.port.out.RegisterMembershipPort;
import com.payment.membership.domain.Membership;
import common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {

        return membershipRepository.save(
                new MembershipJpaEntity(
                        membershipName.getNameValue()
                        , membershipEmail.getEmailValue()
                        , membershipAddress.getAddressValue()
                        , membershipIsValid.isValidValue()
                        , membershipIsCorp.isCorpValue()
                )
        );
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {

        return membershipRepository.findById(Long.parseLong(membershipId.getMembershipId())).orElse(null);
    }
}
