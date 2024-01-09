package com.payment.membership.application.port.in;

import com.payment.membership.domain.Membership;


public interface RegisterMembershipUseCase {

    Membership registerMembership(RegisterMembershipCommand command);
}
