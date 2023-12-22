package com.payment.membership.application.port.in;

import com.payment.membership.domain.Membership;
import common.UseCase;


public interface RegisterMembershipUseCase {

    Membership registerMembership(RegisterMembershipCommand command);
}
