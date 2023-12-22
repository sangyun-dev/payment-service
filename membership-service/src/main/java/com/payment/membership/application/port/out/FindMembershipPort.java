package com.payment.membership.application.port.out;

import com.payment.membership.adapter.out.persistence.MembershipJpaEntity;
import com.payment.membership.domain.Membership;

public interface FindMembershipPort {

    MembershipJpaEntity findMembership(
            Membership.MembershipId membershipId
    );
}
