package com.payment.membership.adapter.in.web;

import com.payment.membership.application.port.in.ModifyMembershipCommand;
import com.payment.membership.application.port.in.ModifyMembershipUseCase;
import com.payment.membership.domain.Membership;
import com.payment.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {

    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @PostMapping(path = "/membership/modify")
    ResponseEntity<Membership> modifyMembershipByMembershipId(@RequestBody ModifyMembershipRequest request) {

        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .membershipId(request.getMembershipId())
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .isValid(request.isValid())
                .isCorp(request.isCorp())
                .build();

        return ResponseEntity.ok(modifyMembershipUseCase.modifyMembership(command));
    }
}
