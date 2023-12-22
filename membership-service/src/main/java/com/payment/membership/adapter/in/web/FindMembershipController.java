package com.payment.membership.adapter.in.web;

import com.payment.membership.application.port.in.FindMembershipCommand;
import com.payment.membership.application.port.in.FindMembershipUseCase;
import com.payment.membership.domain.Membership;
import common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindMembershipController {

    private final FindMembershipUseCase findMembershipUseCase;

    @GetMapping(path = "membership/{membershipId}")
    ResponseEntity<Membership> findMembershipByMembershipId(@PathVariable String membershipId) {

        FindMembershipCommand findMembershipCommand = FindMembershipCommand.builder()
                .membershipId(membershipId)
                .build();

        return ResponseEntity.ok(findMembershipUseCase.findMembership(findMembershipCommand));
    }
}
